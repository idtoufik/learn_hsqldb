package fr.learn.compilation;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CompilationController {

	Compilation comp = new Compilation();

	@RequestMapping(value="/resources/compileC", method = RequestMethod.POST)
	public String compileC(@RequestBody String code){
		File temp = null;
		try {
			temp = new File("tmp/code.c");
			BufferedWriter bw = new BufferedWriter(new FileWriter(temp));
			bw.write(code);
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		String log = comp.compile("gcc tmp/code.c -o tmp/code");
		if(log.equals("successful")){
			return comp.run("./tmp/code");
		}else{
			return log;
		}
	}

	@RequestMapping(value="/resources/compileCpp", method = RequestMethod.POST)
	public String compileCpp(@RequestBody String code){
		File temp = null;
		try {
			temp = new File("tmp/codepp.cpp");
			BufferedWriter bw = new BufferedWriter(new FileWriter(temp));
			bw.write(code);
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		String log = comp.compile("g++ tmp/codepp.cpp -o tmp/codepp");
		if(log.equals("successful")){
			return comp.run("./tmp/codepp");
		}else{
			return log;
		}
	}

	@RequestMapping(value="/resources/compilePython", method = RequestMethod.POST)
	public String compilePython(@RequestBody String code){
		File temp = null;
		try {
			temp = new File("tmp/codepy.py");
			BufferedWriter bw = new BufferedWriter(new FileWriter(temp));
			bw.write(code);
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return comp.run("python tmp/codepy.py");
	}

	@RequestMapping(value="/resources/getCode", method = RequestMethod.POST)
	public String getCode(@RequestBody String code){
		File temp = null;
		try {
			temp = new File("tmp/Code.java");
			BufferedWriter bw = new BufferedWriter(new FileWriter(temp));
			bw.write(code);
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		String log = comp.compile("javac tmp/Code.java");
		if(log.equals("successful")){
			return comp.run("java tmp/Code");
		}else{
			return log;
		}
	}

}
