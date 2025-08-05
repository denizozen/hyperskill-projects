package advisor;

import java.util.List;

public class PageLister<T> {
    private List<T> items;
    private int pageSize;
    private int currentPage;

    public PageLister(List<T> items, int pageSize) {
        this.items = items;
        this.pageSize = pageSize;
        this.currentPage = 0;
    }

    public void showCurrentPage(){
        int start = currentPage * pageSize;
        int end = Math.min(start + pageSize, items.size());

        for (int i = 0; i < end; i++) {
            System.out.println(items.get(i));
        }

        int totalPages = (items.size() + pageSize - 1) / pageSize;
        System.out.println("---PAGE " + (currentPage + 1) + " OF " + totalPages + "---");

    }

    public void next() {
        if ((currentPage + 1) * pageSize >= items.size()) {
            System.out.println("No more pages.");
        } else {
            currentPage++;
            showCurrentPage();
        }
    }

    public void prev() {
        if (currentPage == 0) {
            System.out.println("No more pages.");
        } else {
            currentPage--;
            showCurrentPage();
        }
    }
}
