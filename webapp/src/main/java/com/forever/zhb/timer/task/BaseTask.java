package com.forever.zhb.timer.task;

import java.util.TimerTask;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public abstract class BaseTask extends TimerTask{

    protected Log log;
    
    public BaseTask(){
        log = LogFactory.getLog(getClass());
    }

    @Override
    public void run() {
        boolean commit = false;
        try {
            //LocalHibernateTransactionController.beginTransaction();
            doRun();
            commit = true;
        } catch (Exception e) {
            commit = false;
            log.error("error!", e);
        } finally {
            //LocalHibernateTransactionController.commit(commit);
        }
    }
    
    public abstract void doRun() throws Exception;
}
