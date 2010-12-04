package ZetaFish;

/**
 * Contains the version of the application 
 */
public class VersionInfo {
	public static final String revisionMajor="1";
	public static final String revisionMinor="0";
	public static final String revisionRevision="4";
	
	/**
	 * Get the full version in string format.
	 * @return version string
	 */
	public static String version()
	{
		return revisionMajor + "." + revisionMinor + "." + revisionRevision;
	}
	
}
