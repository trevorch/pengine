package org.bizboost.pengine;

import com.alibaba.fastjson.JSON;
import org.apache.commons.io.FileUtils;
import org.bizboost.pengine.bean.exception.IllegalActionFormat;
import org.bizboost.pengine.util.PromotionActionTool;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.File;
import java.io.IOException;
/**
 * @author ：cdm
 * @date ：Created in 2019/6/28 22:55
 * @description：
 * @modified By：
 * @version: 0.1.0$
 */
public class ScriptEngineTest {
	private static final Logger log = LoggerFactory.getLogger(ScriptEngineTest.class);
	private final static ScriptEngineManager manager = new ScriptEngineManager();
	@Test
	public void test() throws ScriptException, IOException, NoSuchMethodException {
		StackTraceElement fr = Thread.currentThread().getStackTrace()[1];

		String path = ScriptEngineTest.class.getClassLoader().getResource("funtions.js").getPath();
		String funcs = FileUtils.readFileToString(new File(path),"utf8");

		ScriptEngine engine = manager.getEngineByName("js");

		engine.eval(funcs);

		//String s = StringUtils.trimWhitespace(" 99 88 77     ");


		try {
			log.info(JSON.toJSONString(PromotionActionTool.functionalize("a reduce 50 "),true));
		} catch (IllegalActionFormat e) {
			log.error("MethodName[{}()],LineNumber[{}],ErrorMessage[{}]",fr.getMethodName(),fr.getLineNumber(),e.getMessage());
			e.printStackTrace();
			e.printStackTrace();
		}

		if(engine instanceof Invocable){
			Invocable in = (Invocable)engine;
			log.info("{}",in.invokeFunction("gift"));
			log.info("{}",in.invokeFunction("coupon"));
			log.info("{}",in.invokeFunction("couapon"));
		}




	}

	@Test
	public void splitTest(){
		String strength = "85B2A6E48E2A419CBF23AC41AE467F97:2|85B2A6E48E2A419CBF23AC41AE467F98:3";
		String[] items = strength.split("\\|");
		items = strength.split(":");
		log.info("{}",JSON.toJSONString(items));
	}

}
