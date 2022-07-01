package com.sunfujun.tools.io.core.groovy;

import groovy.lang.GroovyShell;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class GroovyScriptEngine implements BeanPostProcessor {

        private Log logger = LogFactory.getLog(GroovyScriptEngine.class);


        private GroovyBinding binding = new GroovyBinding();


        private List<String> bindingInterface = Arrays.asList("cn.com.coho.tools.io.core.groovy.GroovyScriptEngine.IScriptService");


        public GroovyScriptEngine() {


        }


        public List<String> getBindingInterface() {


            return this.bindingInterface;


        }


        public void setBindingInterface(List<String> bindingInterface) {


            this.bindingInterface = bindingInterface;


        }


        public void execute(String script) {


            this.executeObject(script, (Map) null);


        }


        public void execute(String script, Map<String, Object> vars) {


            this.executeObject(script, vars);


        }


        public boolean executeBoolean(String script, Map<String, Object> vars) {


            return ((Boolean) this.executeObject(script, vars)).booleanValue();


        }


        public String executeString(String script, Map<String, Object> vars) {


            return (String) this.executeObject(script, vars);


        }


        public int executeInt(String script, Map<String, Object> vars) {


            return ((Integer) this.executeObject(script, vars)).intValue();


        }


        public float executeFloat(String script, Map<String, Object> vars) {


            return ((Float) this.executeObject(script, vars)).floatValue();


        }

    /**
     * 执行groovy
     * @param script
     * @param vars
     * @return
     */
        public Object executeObject(String script, Map<String, Object> vars) {


            this.binding.clearVariables();


            this.logger.debug("执行脚本:" + script);


            GroovyShell shell = new GroovyShell(this.binding);


            this.setParameters(shell, vars);


            script = script.replace("&apos;", "'").replace("&quot;", "\"").replace("&gt;", ">").replace("&lt;", "<").replace("&nuot;", "\n").replace("&amp;", "&");


            return shell.evaluate(script);


        }


        private void setParameters(GroovyShell shell, Map<String, Object> vars) {


            if (vars != null) {


                Set<Map.Entry<String, Object>> set = vars.entrySet();


                Iterator it = set.iterator();


                while (it.hasNext()) {


                    Map.Entry<String, Object> entry = (Map.Entry) it.next();


                    shell.setVariable((String) entry.getKey(), entry.getValue());


                }


            }


        }


        public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {


            List<Class<?>> list = new ArrayList();


            Iterator var4 = this.bindingInterface.iterator();


            while (var4.hasNext()) {


                String str = (String) var4.next();


                try {


                    Class<?> clazz = Class.forName(str);


                    list.add(clazz);


                } catch (ClassNotFoundException var7) {


                    var7.printStackTrace();


                    this.logger.debug(var7.getException());


                }


            }


            var4 = list.iterator();


            while (var4.hasNext()) {


                Class<?> clazz = (Class) var4.next();


                boolean isImpl = BeanGroovyUtils.isInherit(bean.getClass(), clazz);


                if (isImpl && isImpl) {


                    this.binding.setProperty(beanName, bean);


                }


            }


            return bean;


        }


        public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {


            return bean;


        }



}
