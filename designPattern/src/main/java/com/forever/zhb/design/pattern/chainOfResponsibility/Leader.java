package com.forever.zhb.design.pattern.chainOfResponsibility;

public abstract class Leader {
	
	protected String name;
	protected Leader nextLeader;
	
	public Leader(String name){
		this.name = name;
	}
	
	public abstract void handerRequest(LeaveRequest leaveRequest);

	public Leader getNextLeader() {
		return nextLeader;
	}

	public void setNextLeader(Leader nextLeader) {
		this.nextLeader = nextLeader;
	}

}
