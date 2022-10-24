import java.text.SimpleDateFormat

def date = new Date()
sdf = new SimpleDateFormat("yyyyMMdd")
return sdf.format(date)