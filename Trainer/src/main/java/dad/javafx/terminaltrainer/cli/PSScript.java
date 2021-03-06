package dad.javafx.terminaltrainer.cli;

import java.io.File;

import org.apache.commons.lang.StringUtils;


public class PSScript extends Command {

	private static final String PS = "powershell";

	public PSScript() {
		this("");
	}

	public PSScript(File scriptFile, String ... params) {
		this(false, scriptFile.getAbsolutePath(), params);
	}

	public PSScript(String scriptPath, String ... params) {
		this(false, scriptPath, params);
	}

	public PSScript(boolean asAdmin, File scriptFile, String ... params) {
		this(asAdmin, scriptFile.getAbsolutePath(), params);
	}

	public PSScript(boolean asAdmin, String scriptPath, String ... params) {
		super(PS);
		getArguments().addAll("-NoProfile", "-WindowStyle", "Hidden");
		if (asAdmin) {
			String elevatedScript = (scriptPath + " " + StringUtils.join(params, " ")).trim();
			getArguments().addAll("-Command", "Start-Process -FilePath powershell -ArgumentList '-File " + elevatedScript + "' -Verb RunAs");
		} else { 
			getArguments().addAll("-ExecutionPolicy", "Bypass", "-File", scriptPath);
			getArguments().addAll(params);
		}
	}

}
