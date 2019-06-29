package org.bizboost.pengine;

import org.apache.commons.io.FileUtils;
import org.bizboost.pengine.bean.exception.IllegalActionFormat;
import org.bizboost.pengine.util.FunctionTool;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.File;
import java.io.IOException;

public class ScriptEngineTest {
	private static final Logger log = LoggerFactory.getLogger(ScriptEngineTest.class);
	private final static ScriptEngineManager manager = new ScriptEngineManager();
	@Test
	public void test() throws ScriptException, IOException, NoSuchMethodException, IllegalActionFormat {
		String path = ScriptEngineTest.class.getClassLoader().getResource("funtions.js").getPath();
		String funcs = FileUtils.readFileToString(new File(path),"utf8");

		ScriptEngine engine = manager.getEngineByName("js");

		engine.eval(funcs);

		//String s = StringUtils.trimWhitespace(" 99 88 77     ");

		log.info(FunctionTool.genFuncFromAction(" reduce 50 "));

		if(engine instanceof Invocable){

			Invocable in = (Invocable)engine;

			log.info("{}",in.invokeFunction("gift"));
			log.info("{}",in.invokeFunction("coupon"));
			log.info("{}",in.invokeFunction("couapon"));
		}

	}

}
