package ZetaFish;

/**
 * Contains the version of the application 
 */
public class VersionInfo 
{
	/**
	 * Incremented for significant addition of feature/functionality.
	 */
	public static final String revisionMajor="1";
	/**
	 * Incremented for slight modification to existing feature/functionality.  
	 * Reset to zero on an increment of the Major number.
	 */
	public static final String revisionMinor="0";
	/**
	 * Incremented on bug fix or any rebuild.  
	 * Reset to zero on an increment of the Major number.
	 */
	public static final String revisionRevision="5";
	
	/**
	 * Get the full version in string format.
	 * @return version string
	 */
	public static String version()
	{
		return revisionMajor + "." + revisionMinor + "." + revisionRevision;
	}
	
}
