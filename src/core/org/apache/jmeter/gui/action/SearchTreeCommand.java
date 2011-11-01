/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package org.apache.jmeter.gui.action;

import java.awt.event.ActionEvent;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.swing.JOptionPane;

import org.apache.commons.lang.StringUtils;
import org.apache.jmeter.gui.GuiPackage;
import org.apache.jmeter.gui.Searchable;
import org.apache.jmeter.gui.tree.JMeterTreeModel;
import org.apache.jmeter.gui.tree.JMeterTreeNode;
import org.apache.jmeter.util.JMeterUtils;
import org.apache.jorphan.logging.LoggingManager;
import org.apache.log.Logger;

/**
 * Search nodes for a text
 * TODO Enhance search dialog to select kind of nodes ....
 */
public class SearchTreeCommand extends AbstractAction {
    private Logger logger = LoggingManager.getLoggerForClass();

    private static final Set<String> commands = new HashSet<String>();

    static {
        commands.add(ActionNames.SEARCH_TREE);
    }

    /**
     * @see Command#doAction(ActionEvent)
     */
    @Override
    public void doAction(ActionEvent e) {
        String wordToSearch = JOptionPane.showInputDialog(
                GuiPackage.getInstance().getMainFrame(),
                JMeterUtils.getResString("search_word"),  // $NON-NLS-1$
                JMeterUtils.getResString("search_tree_title"),  // $NON-NLS-1$
                JOptionPane.QUESTION_MESSAGE);
        if(wordToSearch==null) {
            return;
        }
        ActionRouter.getInstance().doActionNow(new ActionEvent(e.getSource(), e.getID(), ActionNames.SEARCH_RESET));
        GuiPackage guiPackage = GuiPackage.getInstance();
        JMeterTreeModel jMeterTreeModel = guiPackage.getTreeModel();
        Set<JMeterTreeNode> nodes = new HashSet<JMeterTreeNode>();
        for (JMeterTreeNode jMeterTreeNode : jMeterTreeModel.getNodesOfType(Searchable.class)) {
            try {
                if (jMeterTreeNode.getUserObject() instanceof Searchable){
                    Searchable searchable = (Searchable) jMeterTreeNode.getUserObject();
                    List<JMeterTreeNode> matchingNodes = jMeterTreeNode.getPathToThreadGroup();
                    if(!StringUtils.isEmpty(wordToSearch)) {
                        boolean result = searchable.searchContent(wordToSearch);
                        if(result) {
                            nodes.addAll(matchingNodes);
                        }
                    }
                }
            } catch (Exception ex) {
                logger.error("Error occured searching for word:"+ wordToSearch, ex);
            }
        }
        for (Iterator<JMeterTreeNode> iterator = nodes.iterator(); iterator.hasNext();) {
            JMeterTreeNode jMeterTreeNode = iterator.next();
            jMeterTreeNode.setMarkedBySearch(true);
        }
        GuiPackage.getInstance().getMainFrame().repaint();
    }


    /**
     * @see Command#getActionNames()
     */
    @Override
    public Set<String> getActionNames() {
        return commands;
    }
}
