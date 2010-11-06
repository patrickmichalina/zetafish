package ZetaFish;

public class VersionInfo {
	public static final String revisionMajor="1";
	public static final String revisionMinor="0";
	public static final String revisionRevision="0";
	
	public static String version()
	{
		return revisionMajor + "." + revisionMinor + "." + revisionRevision;
	}
	
}
