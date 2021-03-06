/**
 * The contents of this file are subject to the license and copyright
 * detailed in the LICENSE and NOTICE files at the root of the source
 * tree and available online at
 *
 * http://www.dspace.org/license/
 */
package org.dspace.app.mediafilter;

import java.io.InputStream;

import org.dspace.content.Bitstream;
import org.dspace.content.Item;
import org.dspace.core.Context;

/**
 * Public interface for any class which transforms or converts content/bitstreams 
 * from one format to another.  This interface should be implemented by any class
 * which defines a "filter" to be run by the MediaFilterManager.
 */
public interface FormatFilter
{
    /**
     * Get a filename for a newly created filtered bitstream
     * 
     * @param sourceName
     *            name of source bitstream
     * @return filename generated by the filter - for example, document.pdf
     *         becomes document.pdf.txt
     */
    public String getFilteredName(String sourceName);

    /**
     * @return name of the bundle this filter will stick its generated
     *         Bitstreams
     */
    public String getBundleName();

    /**
     * @return name of the bitstream format (say "HTML" or "Microsoft Word")
     *         returned by this filter look in the bitstream format registry or
     *         mediafilter.cfg for valid format strings.
     */
    public String getFormatString();

    /**
     * @return string to describe the newly-generated Bitstream's - how it was
     *         produced is a good idea
     */
    public String getDescription();

    /**
     * @param item Item
     * @param source
     *            input stream
     * @param verbose verbosity flag
     * 
     * @return result of filter's transformation, written out to a bitstream
     * @throws Exception if error
     */
    public InputStream getDestinationStream(Item item, InputStream source, boolean verbose)
            throws Exception;

    /**
     * Perform any pre-processing of the source bitstream *before* the actual 
     * filtering takes place in MediaFilterManager.processBitstream().
     * <p>
     * Return true if pre-processing is successful (or no pre-processing
     * is necessary).  Return false if bitstream should be skipped
     * for any reason.
     * 
     * 
     * @param c context
     * @param item item containing bitstream to process
     * @param source source bitstream to be processed
     * @param verbose verbose mode
     * 
     * @return true if bitstream processing should continue, 
     * 			false if this bitstream should be skipped
     * @throws Exception if error
     */
    public boolean preProcessBitstream(Context c, Item item, Bitstream source, boolean verbose)
            throws Exception;
        
    /**
     * Perform any post-processing of the generated bitstream *after* this
     * filter has already been run.
     * <p>
     * Return true if pre-processing is successful (or no pre-processing
     * is necessary).  Return false if bitstream should be skipped
     * for some reason.
     * 
     * 
     * @param c
     *            context
     * @param item
     *            item containing bitstream to process
     * @param generatedBitstream
     *            the bitstream which was generated by
     *            this filter.
     * @throws Exception if error
     */
    public void postProcessBitstream(Context c, Item item, Bitstream generatedBitstream)
            throws Exception;
}

