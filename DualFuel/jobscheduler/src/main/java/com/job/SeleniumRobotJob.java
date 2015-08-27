package com.job;

import com.dualfual.common.DateUtil;
import com.dualfual.remote.EntechRemote;
import com.dualfual.remote.SeleniumRemote;
import com.job.core.MantraJobRegistry;
import com.job.core.base.MantraJob;
import com.job.core.base.MantraJobExecutionResult;
import com.job.core.base.MantraJobParam;
import com.job.helper.RemoteHelper;
import com.job.html.HtmlFieldType;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;

import java.util.List;

@MantraJobRegistry(jobType = "Common : Selenium Robot Job")
public class SeleniumRobotJob extends MantraJob {

    private static Logger log = Logger.getLogger(MantraWSDownload.class);

    @MantraJobParam(displayName = "User Name", fieldType = HtmlFieldType.INPUT, isRequired = true, htmlFieldWidth = 100)
    private String username = "";

    @MantraJobParam(displayName = "Password", fieldType = HtmlFieldType.INPUT, isRequired = true, htmlFieldWidth = 100)
    private String password;

    @MantraJobParam(displayName = "Login URL", fieldType = HtmlFieldType.INPUT, isRequired = true, htmlFieldWidth = 600)
    private String loginUrl;

    @MantraJobParam(displayName = "Temp Folder", fieldType = HtmlFieldType.INPUT, isRequired = true, htmlFieldWidth = 100)
    private String temp;

    @MantraJobParam(displayName = "Class", fieldType = HtmlFieldType.INPUT, isRequired = true, htmlFieldWidth = 400)
    private String clazz = "com.job.helper.EntechRemoteHelper";

    @Override
    public MantraJobExecutionResult execute() throws Exception {

        try {
            RemoteHelper helper = (RemoteHelper)Class.forName(clazz).newInstance();
            helper.performWork(loginUrl, username, password, temp);

        } catch (ClassNotFoundException e) {
            return MantraJobExecutionResult.createError(e.getMessage());
        }catch (Exception e) {
            return MantraJobExecutionResult.createRetryOpetation(e.getMessage());
        }

        return MantraJobExecutionResult.createSucess("Job Completed.");
    }
}
