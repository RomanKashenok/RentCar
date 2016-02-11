package com.kashenok.rentcar.owntag;

import com.kashenok.rentcar.entity.Order;
import com.kashenok.rentcar.resource.MessageManager;
import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.JspWriter;
import static javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * class OrderTabMaker.Class for processing order table making tag.
 */
public class OrderTabMaker extends TagSupport {

    private static final long serialVersionUID = 5812857221699544284L;

    private ArrayList<Order> orderList;
    private int rows, count;
    private String buttonChange;
    private String buttonDelete;

    /**
     * method setOrderList. Initialize List of orders.
     *
     * @param orderList is List of Order objects.
     */
    public void setOrderList(ArrayList<Order> orderList) {
        this.orderList = orderList;
    }

    /**
     * method setRows. Initialize rows number.
     *
     * @param rows is rows quantity.
     */
    public void setRows(int rows) {
        this.rows = rows;
    }

    /*
     * @see javax.servlet.jsp.tagext.TagSupport#doStartTag()
     */
    @Override
    public int doStartTag() throws JspTagException {
        try {
            count = 0;
            buttonChange = (new MessageManager()).getProperty("button.change");
            buttonDelete = (new MessageManager()).getProperty("button.delete");
            setRows(orderList.size());
            JspWriter out = pageContext.getOut();
            out.write("<p>");
        } catch (IOException e) {

            throw new JspTagException(e.getMessage());
        }
        return EVAL_BODY_INCLUDE;
    }

    /*
     * method doAfterBody. Writing table with Order information in jsp
     */
    @Override
    public int doAfterBody() throws JspTagException {

        if (count < rows) {
            try {
                if (orderList.get(count) != null) {
                    pageContext.getOut().write("<table cellpadding=\"7\" cellspacing=\"0\" class=\"conttable\"><tbody><tr>"
                            + "<td><table cellpadding=\"1\" cellspacing=\"0\" class=\"ordertable\">"
                            + "<tr><td>"
                            + orderList.get(count).getCar().getManufacturer() + " | " + orderList.get(count).getCar().getModel() + " | " + orderList.get(count).getCar().getIssueDate()
                            + "</td></tr>"
                            + "<tr><td>"
                            + orderList.get(count).getDateFrom()
                            + "</td></tr>"
                            + "<tr><td>"
                            + orderList.get(count).getDateTo()
                            + "</td></tr>"
                            + "<tr><td>"
                            + orderList.get(count).getOrderStatus()
                            + "</td></tr>"
                            + "<tr><td>"
                            + orderList.get(count).getCoast()
                            + "</td></tr></table></td>"
                            + "<td>"
                            + "<form method=\"POST\" action=\"controller\">"
                            + "<input type=\"hidden\" name=\"command\" value=\"orderneedchange\" />"
                            + "<input type=\"hidden\" name=\"orderId\" value=\"" + orderList.get(count).getOrderId() + "\">"
                            + "<button type=\"submit\" class=\"submit_button\">" + buttonChange + "</button>"
                            + "</form>"
                            + "<form method=\"POST\" action=\"controller\">"
                            + "<input type=\"hidden\" name=\"command\" value=\"orderneeddelete\" />"
                            + "<input type=\"hidden\" name=\"orderId\" value=\"" + orderList.get(count).getOrderId() + "\">"
                            + "<button type=\"submit\" class=\"submit_button\">" + buttonDelete + "</button>"
                            + "</form>"
                            + "</td>"
                            + "</tr></tbody></table>"
                    );
                }
            } catch (IOException e) {
                throw new JspTagException(e.getMessage());
            }
            count++;
            return EVAL_BODY_AGAIN;
        } else {
            return SKIP_BODY;
        }
    }

    /*
     * @see javax.servlet.jsp.tagext.TagSupport#doStartTag()
     */
    @Override
    public int doEndTag() throws JspTagException {
        try {
            pageContext.getOut().write("</p>");
        } catch (IOException e) {
            System.out.println("Чет не так");

            throw new JspTagException(e.getMessage());
        }
        count = 0;
        return EVAL_PAGE;
    }

}
