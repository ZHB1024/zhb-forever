package com.forever.zhb.design.pattern.chainOfResponsibility;

public class ManagerLeader extends Leader {

	public ManagerLeader(String name) {
		super(name);
	}

	@Override
	public void handerRequest(LeaveRequest leaveRequest) {
		if (leaveRequest.getDays() < 5) {
			System.out.println("请假人："+leaveRequest.getName()+",天数："+leaveRequest.getDays()+",理由："+leaveRequest.getReason());
            System.out.println("审批人："+this.name+" 主任，审批通过！");
		}else{
			this.nextLeader.handerRequest(leaveRequest);
		}
	}

}
