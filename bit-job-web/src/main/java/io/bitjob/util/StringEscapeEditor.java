package io.bitjob.util;

import org.springframework.web.util.JavaScriptUtils;

import java.beans.PropertyEditorSupport;

/**

 * Date: 15-4-9
 * Time: 下午6:06
 */
public class StringEscapeEditor extends PropertyEditorSupport {


    private boolean escapeHTML;// 编码HTML
    private boolean escapeJavaScript;// 编码javascript


    public StringEscapeEditor() {
        super();
    }


    public StringEscapeEditor(boolean escapeHTML, boolean escapeJavaScript) {
        super();
        this.escapeHTML = escapeHTML;
        this.escapeJavaScript = escapeJavaScript;
    }


    @Override
    public String getAsText() {//参数输入过滤
        Object value = getValue();
        return value != null ? value.toString() : "";
    }


    @Override
    public void setAsText(String text) throws IllegalArgumentException {//参数输入过滤、参数输出过滤
        Object source = this.getSource();
        if (text == null) {
            setValue(null);
        } else {
            String value = text;
            if (escapeHTML) {
                // value = HtmlUtils.htmlEscape(value);//参数输入输出都过滤。把富文本参数的格式也过滤掉了
                value = cleanXSS(value);
            }
            if (escapeJavaScript) {
                value = JavaScriptUtils.javaScriptEscape(value);
            }
            setValue(value);
        }
    }

    private String cleanXSS(String value) {
        // value = value.replaceAll("<", "&lt;").replaceAll(">", "&gt;"); //富文本要使用，所以注释掉
        value = value.replaceAll("\\(", "&#40;").replaceAll("\\)", "&#41;");
        value = value.replaceAll("'", "&#39;");
        value = value.replaceAll("eval\\((.*)\\)", "");
        value = value.replaceAll("[\\\"\\\'][\\s]*javascript:(.*)[\\\"\\\']", "\"\"");
        value = value.replaceAll("script", "");
        value = value.replaceAll("alert", "");
        return value;
    }
}
