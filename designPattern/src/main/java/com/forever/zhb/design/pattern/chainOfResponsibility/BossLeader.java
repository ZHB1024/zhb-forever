package com.forever.zhb.design.pattern.chainOfResponsibility;

public class BossLeader extends Leader {

	public BossLeader(String name) {
		super(name);
	}

	@Override
	public void handerRequest(LeaveRequest leaveRequest) {
		if (leaveRequest.getDays() < 10) {
			System.out.println("请假人："+leaveRequest.getName()+",天数："+leaveRequest.getDays()+",理由："+leaveRequest.getReason());
            System.out.println("审批人："+this.name+" 老板，审批通过！");
		}else{
			System.out.println("审批人："+this.name+" 老板，不予批准！");
		}
	}

}
