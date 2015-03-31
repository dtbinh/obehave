package org.obehave.view.components.tree.edit;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import org.obehave.exceptions.ServiceException;
import org.obehave.model.Action;
import org.obehave.model.Node;
import org.obehave.service.Study;
import org.obehave.view.util.AlertUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author Markus Möslinger
 */
public class ActionGroupEditControl {
    private static final Logger log = LoggerFactory.getLogger(ActionGroupEditControl.class);

    private Node loadedActionNode;
    private Runnable saveCallback;

    private boolean edit;

    private Study study;

    @FXML
    private TextField name;

    @FXML
    private CheckBox exclusive;

    public String getName() {
        return name.getText();
    }

    public void setName(String name) {
        this.name.setText(name);
    }

    public void loadActionGroupEdit(Node node) {
        loadedActionNode = node;
        edit = true;

        setName(node.getTitle());
        exclusive.setSelected(node.getExclusivity() != Node.Exclusivity.NOT_EXCLUSIVE);

        name.requestFocus();
    }

    public void loadActionGroupNew(Node parent) {
        loadedActionNode = parent;
        edit = false;

        setName("");
        exclusive.setSelected(false);

        name.requestFocus();
    }

    public void saveCurrent() {
        if (name.getText().isEmpty()) {
            AlertUtil.showError("Validation error", "Action group must have a name");
            return;
        }

        Node node;

        if (!edit) {
            log.debug("Creating new action");
            node = new Node(Action.class);
            loadedActionNode.addChild(node);
        } else {
            node = loadedActionNode;
        }

        node.setTitle(name.getText());

        if (exclusive.isSelected()) {
            node.setExclusivity(Node.Exclusivity.TOTAL_EXCLUSIVE);
        } else {
            node.setExclusivity(Node.Exclusivity.NOT_EXCLUSIVE);
        }

        try {

            study.getNodeService().save(study.getActions());

            loadedActionNode = null;
            saveCallback.run();
        } catch (ServiceException exception) {
            AlertUtil.showError("Error", exception.getMessage());
        }
    }

    public void cancel() {
        saveCallback.run();
    }

    public void setSaveCallback(Runnable saveCallback) {
        this.saveCallback = saveCallback;
    }

    public void setStudy(Study study) {
        this.study = study;
    }
}