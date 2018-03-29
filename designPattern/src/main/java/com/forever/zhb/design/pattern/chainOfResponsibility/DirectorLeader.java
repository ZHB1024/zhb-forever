package com.forever.zhb.design.pattern.chainOfResponsibility;

public class DirectorLeader extends Leader {

	public DirectorLeader(String name) {
		super(name);
	}

	@Override
	public void handerRequest(LeaveRequest leaveRequest) {
		if (leaveRequest.getDays() < 3) {
			System.out.println("请假人："+leaveRequest.getName()+",天数："+leaveRequest.getDays()+",理由："+leaveRequest.getReason());
            System.out.println("审批人："+this.name+" 项目经理，审批通过！");
		}else{
			this.nextLeader.handerRequest(leaveRequest);
		}
	}

}
