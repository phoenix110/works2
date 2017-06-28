package com.cernol.works.web.systemkey;

import com.cernol.works.entity.SystemKey;
import com.haulmont.cuba.core.entity.Entity;
import com.haulmont.cuba.gui.components.*;
import com.haulmont.cuba.gui.components.actions.CreateAction;
import com.haulmont.cuba.gui.components.actions.EditAction;
import com.haulmont.cuba.gui.components.actions.RemoveAction;
import com.haulmont.cuba.gui.data.CollectionDatasource;
import com.haulmont.cuba.gui.data.DataSupplier;
import com.haulmont.cuba.gui.data.Datasource;
import com.haulmont.cuba.security.entity.EntityOp;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Collections;
import java.util.Map;
import java.util.UUID;

public class SystemKeyBrowse extends AbstractLookup {

    /**
     * The {@link CollectionDatasource} instance that loads a list of {@link SystemKey} records
     * to be displayed in {@link SystemKeyBrowse#systemKeysTable} on the left
     */
    @Inject
    private CollectionDatasource<SystemKey, UUID> systemKeysDs;

    /**
     * The {@link Datasource} instance that contains an instance of the selected entity
     * in {@link SystemKeyBrowse#systemKeysDs}
     * <p/> Containing instance is loaded in {@link CollectionDatasource#addItemChangeListener}
     * with the view, specified in the XML screen descriptor.
     * The listener is set in the {@link SystemKeyBrowse#init(Map)} method
     */
    @Inject
    private Datasource<SystemKey> systemKeyDs;

    /**
     * The {@link Table} instance, containing a list of {@link SystemKey} records,
     * loaded via {@link SystemKeyBrowse#systemKeysDs}
     */
    @Inject
    private Table<SystemKey> systemKeysTable;

    /**
     * The {@link BoxLayout} instance that contains components on the left side
     * of {@link SplitPanel}
     */
    @Inject
    private BoxLayout lookupBox;

    /**
     * The {@link BoxLayout} instance that contains buttons to invoke Save or Cancel actions in edit mode
     */
    @Inject
    private BoxLayout actionsPane;

    /**
     * The {@link FieldGroup} instance that is linked to {@link SystemKeyBrowse#systemKeyDs}
     * and shows fields of the selected {@link SystemKey} record
     */
    @Inject
    private FieldGroup fieldGroup;

    /**
     * The {@link RemoveAction} instance, related to {@link SystemKeyBrowse#systemKeysTable}
     */
    @Named("systemKeysTable.remove")
    private RemoveAction systemKeysTableRemove;

    @Inject
    private DataSupplier dataSupplier;

    /**
     * {@link Boolean} value, indicating if a new instance of {@link SystemKey} is being created
     */
    private boolean creating;

    @Override
    public void init(Map<String, Object> params) {

        /*
         * Adding {@link com.haulmont.cuba.gui.data.Datasource.ItemChangeListener} to {@link systemKeysDs}
         * The listener reloads the selected record with the specified view and sets it to {@link systemKeyDs}
         */
        systemKeysDs.addItemChangeListener(e -> {
            if (e.getItem() != null) {
                SystemKey reloadedItem = dataSupplier.reload(e.getDs().getItem(), systemKeyDs.getView());
                systemKeyDs.setItem(reloadedItem);
            }
        });

        /*
         * Adding {@link CreateAction} to {@link systemKeysTable}
         * The listener removes selection in {@link systemKeysTable}, sets a newly created item to {@link systemKeyDs}
         * and enables controls for record editing
         */
        systemKeysTable.addAction(new CreateAction(systemKeysTable) {
            @Override
            protected void internalOpenEditor(CollectionDatasource datasource, Entity newItem, Datasource parentDs, Map<String, Object> params) {
                systemKeysTable.setSelected(Collections.emptyList());
                systemKeyDs.setItem((SystemKey) newItem);
                refreshOptionsForLookupFields();
                enableEditControls(true);
            }
        });

        /*
         * Adding {@link EditAction} to {@link systemKeysTable}
         * The listener enables controls for record editing
         */
        systemKeysTable.addAction(new EditAction(systemKeysTable) {
            @Override
            protected void internalOpenEditor(CollectionDatasource datasource, Entity existingItem, Datasource parentDs, Map<String, Object> params) {
                if (systemKeysTable.getSelected().size() == 1) {
                    refreshOptionsForLookupFields();
                    enableEditControls(false);
                }
            }

            @Override
            public void refreshState() {
                if (target != null) {
                    CollectionDatasource ds = target.getDatasource();
                    if (ds != null && !captionInitialized) {
                        setCaption(messages.getMainMessage("actions.Edit"));
                    }
                }
                super.refreshState();
            }

            @Override
            protected boolean isPermitted() {
                CollectionDatasource ownerDatasource = target.getDatasource();
                boolean entityOpPermitted = security.isEntityOpPermitted(ownerDatasource.getMetaClass(), EntityOp.UPDATE);
                if (!entityOpPermitted) {
                    return false;
                }
                return super.isPermitted();
            }
        });

        /*
         * Setting {@link RemoveAction#afterRemoveHandler} for {@link systemKeysTableRemove}
         * to reset record, contained in {@link systemKeyDs}
         */
        systemKeysTableRemove.setAfterRemoveHandler(removedItems -> systemKeyDs.setItem(null));

        disableEditControls();
    }

    private void refreshOptionsForLookupFields() {
        for (Component component : fieldGroup.getOwnComponents()) {
            if (component instanceof LookupField) {
                CollectionDatasource optionsDatasource = ((LookupField) component).getOptionsDatasource();
                if (optionsDatasource != null) {
                    optionsDatasource.refresh();
                }
            }
        }
    }

    /**
     * Method that is invoked by clicking Ok button after editing an existing or creating a new record
     */
    public void save() {
        if (!validate(Collections.singletonList(fieldGroup))) {
            return;
        }
        getDsContext().commit();

        SystemKey editedItem = systemKeyDs.getItem();
        if (creating) {
            systemKeysDs.includeItem(editedItem);
        } else {
            systemKeysDs.updateItem(editedItem);
        }
        systemKeysTable.setSelected(editedItem);

        disableEditControls();
    }

    /**
     * Method that is invoked by clicking Cancel button, discards changes and disables controls for record editing
     */
    public void cancel() {
        SystemKey selectedItem = systemKeysDs.getItem();
        if (selectedItem != null) {
            SystemKey reloadedItem = dataSupplier.reload(selectedItem, systemKeyDs.getView());
            systemKeysDs.setItem(reloadedItem);
        } else {
            systemKeyDs.setItem(null);
        }

        disableEditControls();
    }

    /**
     * Enabling controls for record editing
     * @param creating indicates if a new instance of {@link SystemKey} is being created
     */
    private void enableEditControls(boolean creating) {
        this.creating = creating;
        initEditComponents(true);
        fieldGroup.requestFocus();
    }

    /**
     * Disabling editing controls
     */
    private void disableEditControls() {
        initEditComponents(false);
        systemKeysTable.requestFocus();
    }

    /**
     * Initiating edit controls, depending on if they should be enabled/disabled
     * @param enabled if true - enables editing controls and disables controls on the left side of the splitter
     *                if false - visa versa
     */
    private void initEditComponents(boolean enabled) {
        fieldGroup.setEditable(enabled);
        actionsPane.setVisible(enabled);
        lookupBox.setEnabled(!enabled);
    }
}