package ohih.sprout.domain;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

public class Utilities {

    public static String createUUID() {
        String uuid = UUID.randomUUID().toString();
        return uuid;
    }

    public static String getExt(String fileName) {
        int pos = fileName.lastIndexOf(".");
        return fileName.substring(pos + 1);
    }

    public static String getIp(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");

        if (ip == null) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null) {
            ip = request.getRemoteAddr();
        }

        return ip;
    }

    public static Paging getPaging(Long totalCount, Paging paging, Integer listSize) {

        Integer numOfPages = paging.getPages();

        Integer presentPage = paging.getPresentPage();

        Integer totalNumOfPages;
        Integer startPage;
        Integer endPage;

        Long firstContent;


        if (presentPage == null) {
            presentPage = 0;
        }

        totalNumOfPages = new Double(Math.ceil(totalCount / listSize.floatValue())).intValue();

        if (presentPage == 0) {
            startPage = 0;
        } else {
            Integer temp = presentPage;
            while (true) {
                if (temp % numOfPages == 0) {
                    startPage = temp;
                    break;
                } else {
                    temp--;
                }
            }
        }

        endPage = startPage + numOfPages - 1;
        if (endPage >= totalNumOfPages) {
            endPage = totalNumOfPages - 1;
        }
        if (endPage < 0) {
            endPage = 0;
        }

        firstContent = (long) presentPage * listSize;

        paging.setListSize(listSize);
        paging.setTotalCount(totalCount);
        paging.setTotalPages(totalNumOfPages);
        paging.setPresentPage(presentPage);
        paging.setStartPage(startPage);
        paging.setEndPage(endPage);
        paging.setFirstItemIndex(firstContent);

        return paging;
    }


}
