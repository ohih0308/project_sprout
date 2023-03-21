package ohih.sprout.session;

import ohih.sprout.domain.IntegerConst;
import ohih.sprout.domain.StringConst;
import ohih.sprout.domain.file.dto.FileDto;
import ohih.sprout.domain.user.dto.LoginMemberDto;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Component
public class SessionMethods {

    public Object getSessionAttribute(HttpServletRequest request, String attribute) {
        HttpSession session = request.getSession();

        return session.getAttribute(attribute);
    }

    public void invalidateSession(HttpServletRequest request) {
        HttpSession session = request.getSession(false);

        if (session != null) {
            session.invalidate();
        }
    }

    public void removerAttribute(HttpServletRequest request, String attributeName) {
        HttpSession session = request.getSession();

        session.removeAttribute(attributeName);
    }


    // set session attribute
    public void setSessionAttribute(HttpServletRequest request, String attributeName, String value) {
        HttpSession session = request.getSession();

        session.setAttribute(attributeName, value);
    }

    public void setSessionAttribute(HttpServletRequest request, String attributeName, Long value) {
        HttpSession session = request.getSession();

        session.setAttribute(attributeName, value);
    }


    // set email verification code
    public void setVerificationCode(HttpServletRequest request, String code, String address) {
        HttpSession session = request.getSession();

        session.setAttribute(StringConst.VERIFICATION_CODE, code);
        session.setAttribute(code, address);
    }


    // login
    public void login(HttpServletRequest request, LoginMemberDto loginMemberDto) {
        HttpSession session = request.getSession();

        session.setAttribute(StringConst.LOGIN_MEMBER, loginMemberDto);
    }


    // update user information
    public void updateName(HttpServletRequest request, String name) {
        HttpSession session = request.getSession(false);

        LoginMemberDto loginMemberDto = (LoginMemberDto) session.getAttribute(StringConst.LOGIN_MEMBER);
        loginMemberDto.setName(name);

        session.setAttribute(StringConst.LOGIN_MEMBER, loginMemberDto);
    }

    public void updateProfileImage(HttpServletRequest request, FileDto fileDto) {
        HttpSession session = request.getSession();

        LoginMemberDto loginMemberDto = (LoginMemberDto) session.getAttribute(StringConst.LOGIN_MEMBER);

        loginMemberDto.setSavedFileName(fileDto.getSavedFileName());
        loginMemberDto.setExt(fileDto.getExt());

        session.setAttribute(StringConst.LOGIN_MEMBER, loginMemberDto);
    }

    public Boolean isAdmin(HttpServletRequest request) {
        HttpSession session = request.getSession();
        LoginMemberDto loginMemberDto = (LoginMemberDto) session.getAttribute(StringConst.LOGIN_MEMBER);

        if (loginMemberDto == null) {
            return false;
        } else if (loginMemberDto.getUserType() == IntegerConst.ADMIN) {
            return true;
        } else {
            return false;
        }
    }
}
