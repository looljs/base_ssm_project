package club.looli.ssm.base_ssm_project.page;

/**
 * 页面信息
 */
public class Page {
    private int page; //当前页码
    private int rows; //每页显示属性
    private int start; //当前页第一个是第几个
    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public int getStart() {
        return (page-1)*rows;
    }

    public void setStart(int start) {
        this.start = start;
    }
}
