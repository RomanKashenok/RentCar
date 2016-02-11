package com.kashenok.rentcar.owntag;

import com.kashenok.rentcar.resource.MessageManager;
import java.io.IOException;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import org.apache.log4j.Logger;

/**
 * class Copyright. Class for processing copyright tag.
 */
public class Copyright extends TagSupport {

    public static final Logger LOG = Logger.getLogger(Copyright.class);
    private static final long serialVersionUID = 6676927795317927494L;

    /*
     * @see javax.servlet.jsp.tagext.TagSupport#doStartTag()
     */
    @Override
    public int doStartTag() throws JspException {
        try {
            String copyright = (new MessageManager()).getProperty("copyright");
            JspWriter out = pageContext.getOut();
            String showCopyright = "<p>" + copyright + "</p>";
            out.write(showCopyright);
        } catch (IOException e) {
            LOG.error("IOException in method Copyright.doStartTag", e);
        }
        return SKIP_BODY;
    }

    /*
     * @see javax.servlet.jsp.tagext.TagSupport#doStartTag()
     */
    @Override
    public int doEndTag() throws JspException {
        return EVAL_PAGE;
    }
}
