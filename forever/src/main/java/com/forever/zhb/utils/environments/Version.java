package com.forever.zhb.utils.environments;

public class Version implements Info {
	
	 public static int getRevision() {
	        return 1392090;
	    }

	    public static String getBuildDate() {
	        return "09/30/2012 17:52 GMT";
	    }

	    public static String getVersion() {
	        return "3.4.5" + ((QUALIFIER == null) ? "" : new StringBuilder().append("-").append(QUALIFIER).toString());
	    }

	    public static String getVersionRevision() {
	        return getVersion() + "-" + getRevision();
	    }

	    public static String getFullVersion() {
	        return getVersionRevision() + ", built on " + getBuildDate();
	    }

	    public static void printUsage() {
	        System.out.print(
	                "Usage:\tjava -cp ... org.apache.zookeeper.Version [--full | --short | --revision],\n\tPrints --full version info if no arg specified.");

	        System.exit(1);
	    }

	    public static void main(String[] args) {
	        if (args.length > 1) {
	            printUsage();
	        }
	        if ((args.length == 0) || ((args.length == 1) && (args[0].equals("--full")))) {
	            System.out.println(getFullVersion());
	            System.exit(0);
	        }
	        if (args[0].equals("--short"))
	            System.out.println(getVersion());
	        else if (args[0].equals("--revision"))
	            System.out.println(getVersionRevision());
	        else
	            printUsage();
	        System.exit(0);
	    }

}
