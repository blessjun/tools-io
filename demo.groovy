try{
    //读取文件
    println '文件读取中'
    def file = new File(a)
    if (!file.exists() || !file.isFile()){
        println '文件读取失败:文件不存在'
        return
    }
    println '文件读取成功'
    def json = new groovy.json.JsonSlurper()

    List<groovy.json.JsonBuilder> results = new  ArrayList<>();

//数据处理
    println '数据处理中'
    file.eachLine { line ->
        def param = json.parseText(line)
        groovy.json.JsonBuilder res = new  groovy.json.JsonBuilder()
        res{
            code param["code"]
            name param["name"]
        }
        results.add(res)
    }
    println '数据处理完成'

//写入文件
    println '写入文件中……'
    long begin = new Date().getTime();
    File f = new File(b)
    if (!f.exists() || !f.isFile()) return
    f.withWriter('utf-8'){writer ->
        for (groovy.json.JsonBuilder jsonBuilder: results) {
            writer.writeLine(groovy.json.StringEscapeUtils.unescapeJava(jsonBuilder.toString()))
        }
    }
    long end = new Date().getTime();

    println '文件写入完成'
    println '写入耗时:'+ (end-begin)+"ms"
    return true
}catch(Exception ex){
    println ex.getLocalizedMessage()
    return false
}

