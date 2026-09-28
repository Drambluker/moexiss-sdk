package org.vlaskin.moexiss.service.history;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.vlaskin.moexiss.http.MoexHttpTransport;
import org.vlaskin.moexiss.service.BaseService;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/** Исторические итоги торгов с проверкой таблиц, обязательных полей и курсора. */
public final class HistoryService extends BaseService
{
    /** Создаёт сервис для официального адреса ISS. */
    public HistoryService()
    {
        super();
    }

    /**
     * Создаёт сервис с пользовательским транспортом.
     * @param baseUrl базовый адрес без /iss
     * @param transport HTTP-транспорт
     */
    public HistoryService(String baseUrl, MoexHttpTransport transport)
    {
        super(baseUrl, transport);
    }

    /**
     * Читает одну страницу итогов по бумаге и режиму торгов.
     * @param query период, код и смещение
     * @return страница с исходными денежными единицами
     * @throws IOException при отказе или несовместимом ответе источника
     */
    public HistoryPage<SecurityHistoryRow> getSecurityHistory(HistoryQuery query) throws IOException
    {
        requireParams(query);
        requireTextParameter(query.board(), "board");
        JsonObject response = historyResponse(query, true);
        try
        {
            List<SecurityHistoryRow> rows = new ArrayList<>();
            for (Map<String, JsonElement> row : table(response, "history", "SECID", "BOARDID", "TRADEDATE", "CLOSE", "CURRENCYID"))
                rows.add(new SecurityHistoryRow(text(row, "SECID", true), text(row, "BOARDID", true),
                        LocalDate.parse(text(row, "TRADEDATE", true)), decimal(row, "CLOSE", true),
                        text(row, "CURRENCYID", true), decimal(row, "FACEVALUE", false),
                        text(row, "FACEUNIT", false), decimal(row, "ACCINT", false),
                        text(row, "TRADINGSESSION", false)));
            validateRows(query, rows.stream().map(SecurityHistoryRow::securityCode).toList(),
                    rows.stream().map(SecurityHistoryRow::boardCode).toList(),
                    rows.stream().map(SecurityHistoryRow::date).toList());
            return page(response, query, rows);
        }
        catch (RuntimeException e)
        {
            throw new IOException("Invalid MOEX security history response", e);
        }
    }

    /**
     * Читает одну страницу значений индекса, не определяя его методику.
     * @param query параметры рынка index
     * @return страница индекса
     * @throws IOException при отказе или несовместимом ответе
     */
    public HistoryPage<IndexHistoryRow> getIndexHistory(HistoryQuery query) throws IOException
    {
        requireParams(query);
        if (!"index".equals(query.market()))
            throw new IllegalArgumentException("Index history requires the index market");
        JsonObject response = historyResponse(query, query.board() != null);
        try
        {
            List<IndexHistoryRow> rows = new ArrayList<>();
            for (Map<String, JsonElement> row : table(response, "history", "SECID", "BOARDID", "TRADEDATE", "CLOSE", "CURRENCYID"))
                rows.add(new IndexHistoryRow(text(row, "SECID", true), text(row, "BOARDID", true),
                        LocalDate.parse(text(row, "TRADEDATE", true)), decimal(row, "CLOSE", true),
                        text(row, "CURRENCYID", true), text(row, "TRADINGSESSION", false)));
            validateRows(query, rows.stream().map(IndexHistoryRow::indexCode).toList(),
                    rows.stream().map(IndexHistoryRow::boardCode).toList(),
                    rows.stream().map(IndexHistoryRow::date).toList());
            return page(response, query, rows);
        }
        catch (RuntimeException e)
        {
            throw new IOException("Invalid MOEX index history response", e);
        }
    }

    /**
     * Возвращает явную недоступность полного источника денежных выплат.
     * Описание бумаги, возвращаемое неизвестным ISS-путём, не считается списком выплат.
     * @param securityCode код бумаги
     * @return статус без выдуманного подтверждения отсутствия выплат
     */
    public HistoryDataset<SecurityDistribution> getDistributions(String securityCode)
    {
        requireTextParameter(securityCode, "securityCode");
        return new HistoryDataset<>(HistoryAvailability.UNSUPPORTED, List.of(), false,
                "A complete distribution source has not been verified");
    }

    /**
     * Получает справочник дроблений; он не подтверждает полноту всех корпоративных событий.
     * @param securityCode код бумаги
     * @return исходные коэффициенты и ограничение охвата
     * @throws IOException при отказе или несовместимом ответе
     */
    public HistoryDataset<SecurityCorporateAction> getCorporateActions(String securityCode) throws IOException
    {
        requireTextParameter(securityCode, "securityCode");
        StringBuilder request = new StringBuilder(baseUrl).append("/iss/statistics/engines/stock/splits/")
                .append(encodePathSegment(securityCode)).append(".json");
        pasteBasicRequestParams(request, "splits");
        JsonObject response = getAndParseResponse(request, JsonObject.class);
        try
        {
            List<SecurityCorporateAction> rows = new ArrayList<>();
            for (Map<String, JsonElement> row : table(response, "splits", "SECID", "TRADEDATE", "BEFORE", "AFTER"))
            {
                String code = text(row, "SECID", true);
                BigDecimal before = decimal(row, "BEFORE", true);
                BigDecimal after = decimal(row, "AFTER", true);
                if (!securityCode.equals(code) || before == null || after == null
                        || before.signum() <= 0 || after.signum() <= 0)
                    throw new IOException("Invalid MOEX split ratio or instrument");
                rows.add(new SecurityCorporateAction(code, LocalDate.parse(text(row, "TRADEDATE", true)),
                        before, after));
            }
            return new HistoryDataset<>(HistoryAvailability.SUPPORTED, rows, false,
                    "Split registry does not prove complete coverage of all corporate events");
        }
        catch (RuntimeException e)
        {
            throw new IOException("Invalid MOEX corporate action response", e);
        }
    }

    private JsonObject historyResponse(HistoryQuery query, boolean board) throws IOException
    {
        StringBuilder request = new StringBuilder(baseUrl).append("/iss/history/engines/")
                .append(encodePathSegment(query.engine())).append("/markets/")
                .append(encodePathSegment(query.market()));
        if (board)
            request.append("/boards/").append(encodePathSegment(requireTextParameter(query.board(), "board")));
        request.append("/securities/").append(encodePathSegment(query.instrument())).append(".json");
        pasteBasicRequestParams(request, "history", "history.cursor");
        appendQueryParameter(request, "from", query.from());
        appendQueryParameter(request, "till", query.to());
        appendQueryParameter(request, "start", query.start());
        return getAndParseResponse(request, JsonObject.class);
    }

    private static void validateRows(HistoryQuery query, List<String> codes, List<String> boards,
                                     List<LocalDate> dates) throws IOException
    {
        for (int i = 0; i < codes.size(); i++)
            if (!query.instrument().equals(codes.get(i))
                    || query.board() != null && !query.board().equals(boards.get(i))
                    || dates.get(i).isBefore(query.from()) || dates.get(i).isAfter(query.to()))
                throw new IOException("MOEX returned history outside the requested instrument or range");
    }

    private static <T> HistoryPage<T> page(JsonObject response, HistoryQuery query, List<T> rows) throws IOException
    {
        List<Map<String, JsonElement>> cursor = table(response, "history.cursor", "INDEX", "TOTAL", "PAGESIZE");
        if (cursor.size() != 1)
            throw new IOException("MOEX history cursor must contain exactly one row");
        int start = decimal(cursor.getFirst(), "INDEX", true).intValueExact();
        int total = decimal(cursor.getFirst(), "TOTAL", true).intValueExact();
        int size = decimal(cursor.getFirst(), "PAGESIZE", true).intValueExact();
        int next = Math.addExact(start, rows.size());
        if (start != query.start() || total < 0 || size <= 0 || rows.size() > size
                || next > total || start > total || rows.isEmpty() && start < total)
            throw new IOException("MOEX history cursor is inconsistent or does not advance");
        return new HistoryPage<>(rows, start, total, size, next == total);
    }

    private static List<Map<String, JsonElement>> table(JsonObject response, String name, String... required) throws IOException
    {
        if (!response.has(name) || !response.get(name).isJsonObject())
            throw new IOException("Required MOEX history table is missing: " + name);
        JsonObject table = response.getAsJsonObject(name);
        JsonArray columns = table.getAsJsonArray("columns");
        JsonArray data = table.getAsJsonArray("data");
        if (columns == null || data == null)
            throw new IOException("MOEX history table has no columns or data");
        List<String> headers = new ArrayList<>();
        for (JsonElement column : columns)
        {
            String header = column.getAsString().toUpperCase(Locale.ROOT);
            if (headers.contains(header))
                throw new IOException("MOEX history columns contain duplicate names");
            headers.add(header);
        }
        for (String column : required)
            if (!headers.contains(column))
                throw new IOException("Required MOEX history column is missing: " + column);
        List<Map<String, JsonElement>> result = new ArrayList<>();
        for (JsonElement element : data)
        {
            JsonArray values = element.getAsJsonArray();
            if (values.size() != columns.size())
                throw new IOException("MOEX history row width differs from columns");
            Map<String, JsonElement> row = new LinkedHashMap<>();
            for (int i = 0; i < columns.size(); i++)
                row.put(headers.get(i), values.get(i));
            result.add(row);
        }
        return result;
    }

    private static String text(Map<String, JsonElement> row, String name, boolean required) throws IOException
    {
        JsonElement value = row.get(name);
        if (value == null || value.isJsonNull())
        {
            if (required)
                throw new IOException("Required MOEX history value is missing: " + name);
            return null;
        }
        String result = value.getAsString();
        if (required && result.isBlank())
            throw new IOException("Required MOEX history value is blank: " + name);
        return result;
    }

    private static BigDecimal decimal(Map<String, JsonElement> row, String name, boolean required) throws IOException
    {
        if (!row.containsKey(name))
        {
            if (required)
                throw new IOException("Required MOEX history column is missing: " + name);
            return null;
        }
        JsonElement value = row.get(name);
        return value == null || value.isJsonNull() ? null : value.getAsBigDecimal();
    }
}
