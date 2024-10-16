import java.text.SimpleDateFormat

class DateUtils {

    static def stringToDate(String dateStr) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd")
        Date date = dateFormat.parse(dateStr)
        return date
    }
}
