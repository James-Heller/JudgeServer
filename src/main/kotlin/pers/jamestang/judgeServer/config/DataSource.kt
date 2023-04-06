package pers.jamestang.judgeServer.config

import com.baomidou.mybatisplus.core.MybatisConfiguration
import com.baomidou.mybatisplus.core.config.GlobalConfig
import com.baomidou.mybatisplus.core.incrementer.DefaultIdentifierGenerator
import com.baomidou.mybatisplus.core.injector.DefaultSqlInjector
import com.baomidou.mybatisplus.core.mapper.BaseMapper
import com.baomidou.mybatisplus.core.toolkit.GlobalConfigUtils
import com.zaxxer.hikari.HikariDataSource
import org.apache.ibatis.builder.xml.XMLMapperBuilder
import org.apache.ibatis.logging.slf4j.Slf4jImpl
import org.apache.ibatis.mapping.Environment
import org.apache.ibatis.session.SqlSession
import org.apache.ibatis.session.SqlSessionFactoryBuilder
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory
import java.io.File
import java.io.FileInputStream
import java.net.JarURLConnection

class DataSource {


    fun getSession(): SqlSession? {

        val builder = SqlSessionFactoryBuilder()
        val configuration = MybatisConfiguration()
        initConfiguration(configuration)
        configuration.logImpl = Slf4jImpl::class.java
        configuration.addMappers("pers.jamestang.judgeServer.mapper")
        val globalConfig = GlobalConfig()
        globalConfig.sqlInjector = DefaultSqlInjector()
        globalConfig.identifierGenerator = DefaultIdentifierGenerator()
        globalConfig.superMapperClass = BaseMapper::class.java
        GlobalConfigUtils.setGlobalConfig(configuration, globalConfig)

        val environment = Environment("1", JdbcTransactionFactory(), init())
        configuration.environment = environment
        this.registryMapperXml(configuration)
        val sqlSessionFactory = builder.build(configuration)

        return sqlSessionFactory.openSession()
    }
    private fun init(): HikariDataSource {

        val dataSource = HikariDataSource()

        dataSource.jdbcUrl = Config.jdbcURL
        dataSource.driverClassName = Config.jdbcDriver
        dataSource.username = Config.username
        dataSource.password = Config.password
        dataSource.idleTimeout = Config.IdleTimeout
        dataSource.isAutoCommit = Config.autoCommit
        dataSource.maximumPoolSize = Config.maxPoolSize
        dataSource.minimumIdle = Config.minimumIdle
        dataSource.maxLifetime = Config.maxLifeTime
        dataSource.connectionTestQuery = Config.connectionTestQuery

        return dataSource
    }

    private fun initConfiguration(cfg: MybatisConfiguration){
        cfg.isMapUnderscoreToCamelCase = true
        cfg.isUseGeneratedKeys = true
    }

    private fun registryMapperXml(cfg: MybatisConfiguration){

        val contextClassLoader = Thread.currentThread().contextClassLoader
        val mapper = contextClassLoader.getResources("mapper")

        while (mapper.hasMoreElements()){
            val url = mapper.nextElement()
            if (url.protocol.equals("file")){
                val path = url.path
                val file = File(path)
                val files = file.listFiles()
                for (f in files!!){
                    val fis = FileInputStream(f)
                    val mapperBuilder = XMLMapperBuilder(fis, cfg, f.path, cfg.sqlFragments)
                    mapperBuilder.parse()
                    fis.close()
                }
            }else{
                val urlConnection = url.openConnection() as JarURLConnection
                val jar = urlConnection.jarFile
                val entries = jar.entries()
                while (entries.hasMoreElements()){
                    val jarEntry = entries.nextElement()
                    if (jarEntry.name.endsWith(".xml")){
                        val inputStream = jar.getInputStream(jarEntry)
                        val mapperBuilder = XMLMapperBuilder(inputStream, cfg, jarEntry.name, cfg.sqlFragments)
                        mapperBuilder.parse()
                        inputStream.close()
                    }
                }
            }
        }
    }
}