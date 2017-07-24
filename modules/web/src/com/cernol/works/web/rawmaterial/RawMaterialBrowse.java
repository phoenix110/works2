package com.cernol.works.web.rawmaterial;

import com.haulmont.cuba.gui.components.AbstractLookup;
import com.haulmont.cuba.gui.components.Button;
import com.haulmont.cuba.gui.components.Component;
import com.haulmont.reports.gui.actions.RunReportAction;

import javax.inject.Inject;
import java.util.Map;

public class RawMaterialBrowse extends AbstractLookup {

    @Inject
    private Button printBtn;

    @Override
    public void init(Map<String, Object> params) {
        super.init(params);

        printBtn.setAction(new RunReportAction("report"));

    }
}