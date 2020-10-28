package controller;

import java.util.ArrayList;


/**
 * Contains all the commands did since the start of the software
 * @author Cayrol, Furelaud
 *
 */
public class CommandMemory {
	public  ArrayList<Command> commandList;
	public int currentCommand;
	
	public CommandMemory() {
		this.currentCommand = -1;
		this.commandList = new ArrayList<Command>();
	}
	
	public void addCommand(Command c){
        int i = this.currentCommand +1;
        while(i<commandList.size()){
        	commandList.remove(i);
        }
        this.currentCommand++;
        this.commandList.add(this.currentCommand, c);
		
    }
	
	public void removeCommand(){
		if (currentCommand < commandList.size()-1){
			currentCommand++;
			Command cde = commandList.get(currentCommand);
			cde.doCommand();
		}
		
    }
	
	public void undo(){
		if (currentCommand >= 0){
			Command cde = commandList.get(currentCommand);
			currentCommand--;
			cde.undoCommand();
		}
	}
	
	public void redo(){
		if (currentCommand < commandList.size()-1){
			currentCommand++;
			Command cde = commandList.get(currentCommand);
			cde.doCommand();
		}
	}
	
}
