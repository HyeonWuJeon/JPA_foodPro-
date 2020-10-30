package com.foodPro.demo.member.service;

import com.foodPro.demo.config.common.Address;
import com.foodPro.demo.config.common.ApplicationService;
import com.foodPro.demo.config.exception.MemberDuplicationException;
import com.foodPro.demo.config.exception.UserNotFoundException;
import com.foodPro.demo.member.domain.Member;
import com.foodPro.demo.member.dto.MemberDto;
import com.foodPro.demo.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;


@Service("memberService")
@RequiredArgsConstructor
public class MemberService extends ApplicationService implements UserDetailsService {

    private final MemberRepository memberRepository;

    /**
     * FUNCTION :: 회원가입
     * @param form
     * @return
     */
    public String SignUp(MemberDto.Request form) {
        HashMap<String, Object> rtnMap = returnMap();
        // LINE :: 패스워드 암호화
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        // LINE :: 아이디 중복검사
        validateDuplicateMember(form.getEmail());
        // LINE :: 패스워드 일치검사
        passwordSameChk(form.getPwd(), form.getPwdChk());
        // LINE :: 나이
        int age = Integer.parseInt(form.getBirth().substring(0, 4));
        // LINE :: 저장 + 유효성 검사
        memberRepository.save(new MemberDto.Request().builder()
                .name(form.getName())
                .address(new Address(form.getCity(), form.getZipcode(), form.getStreet()))
                .birth(form.getBirth())
                .email(form.getEmail())
                .low_pwd(form.getPwd())
                .pwd(passwordEncoder.encode(form.getPwd()))
                .phone(form.getPhone())
                .age(Calendar.getInstance().get(Calendar.YEAR) - age)
                .build().toEntity());
        rtnMap.put(AJAX_RESULT_TEXT, AJAX_RESULT_SUCCESS); //성공
        return "redirect:/login";
    }

    /**
     * FUNCTION :: 아이디 중복검사
     *
     * @param userEmail
     * @return
     */
    @Transactional(readOnly = true)
    public void validateDuplicateMember(String userEmail) {
        if (memberRepository.findByEmail(userEmail).isPresent()) {
            throw new MemberDuplicationException("회원 중복 오류");
        }
    }

    /**
     * FUNCTION :: 패스워드 일치 검사
     *
     * @return
     */
    @Transactional(readOnly = true)
    public void passwordSameChk(String pwd, String pwdChk) {
        System.out.println("pwd +\"  ? \"+ pwdChk = " + pwd + "  ? " + pwdChk);
        if (pwd.equals(pwdChk)) {
            throw new IllegalArgumentException("패스워드 일치하지 않음");
        }
    }

    /**
     * FUNCTION :: 인증절차
     *
     * @param userEmail
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public MemberDto.Response loadUserByUsername(String userEmail) throws UsernameNotFoundException {
        return memberRepository.findByEmail(userEmail).map(u ->
                new MemberDto.Response(u, Collections.singleton(new SimpleGrantedAuthority(u.getRole().getValue())))).orElseThrow(()
                -> new UserNotFoundException(userEmail));
    }

    /**
     * FUNCTION :: 회원 전체 조회
     *
     * @return
     */
    @Transactional(readOnly = true)
    public Page<MemberDto.Response> findAllDesc(Pageable pageable, int age) {
        if (age == 0) {
            return memberRepository.findAll(pageable).map(MemberDto.Response::new);
        }
        return memberRepository.findAllDesc(pageable, age).map(MemberDto.Response::new);
    }

    /**
     * FUNCTION :: 회원 개별 조회
     *
     * @param id
     * @return
     */
    @Transactional(readOnly = true)
    public MemberDto.Response findById(Long id) {
        Member entity = memberRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 회원이 없습니다. id=" + id));

        return new MemberDto.Response(entity);
    }

    /**
     * FUNCTION :: 회원 수정
     *
     * @param id
     * @param requestDto
     * @return
     */
    public Long update(Long id, MemberDto.Request requestDto) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 회원이 없습니다. id=" + id));
        member.update(requestDto.getCity(), requestDto.getStreet(), requestDto.getZipcode(), requestDto.getPhone());

        return id;
    }

    public void ExcelDown(HttpServletResponse response, Pageable pageable) throws IOException {
        Workbook workBook = new SXSSFWorkbook();
        Sheet sheet = workBook.createSheet();

        Page<MemberDto.Excel> ExcelDto = memberRepository.findAll(pageable).map(MemberDto.Excel::new);

        /**
         * 디자인 입히기
         */
//        CellStyle greyCellStyle = workBook.createCellStyle();
//        greyCellStyle.app(greyCellStyle, new Color(231, 234, 236));
//
//        CellStyle blueCellStyle = workbook.createCellStyle();
//        applyCellStyle(blueCellStyle, new Color(223, 235, 246));
//
//        CellStyle bodyCellStyle = workbook.createCellStyle();
//        applyCellStyle(bodyCellStyle, new Color(255, 255, 255));

        String fileName = "TEST.xlsx";
        // 헤더를 생성합니다
        int rowIndex = 0;
        Row headerRow = sheet.createRow(rowIndex++);
        Cell headerCell1 = headerRow.createCell(0);
        headerCell1.setCellValue("이름");

        Cell headerCell2 = headerRow.createCell(1);
        headerCell2.setCellValue("나이");

        Cell headerCell3 = headerRow.createCell(2);
        headerCell3.setCellValue("이메일");

        Cell headerCell4 = headerRow.createCell(3);
        headerCell4.setCellValue("생일");

        Cell headerCell5 = headerRow.createCell(4);
        headerCell5.setCellValue("휴대폰번호");

        // 바디에 데이터를 넣어줍니다
        for (MemberDto.Excel dto : ExcelDto) {
            Row bodyRow = sheet.createRow(rowIndex++);

            Cell bodyCell1 = bodyRow.createCell(0);
            bodyCell1.setCellValue(dto.getName());

            Cell bodyCell2 = bodyRow.createCell(1);
            bodyCell2.setCellValue(dto.getAge());

            Cell bodyCell3 = bodyRow.createCell(2);
            bodyCell3.setCellValue(dto.getEmail());

            Cell bodyCell4 = bodyRow.createCell(3);
            bodyCell4.setCellValue(dto.getBirth());

            Cell bodyCell5 = bodyRow.createCell(4);
            bodyCell5.setCellValue(dto.getBirth());
        }

        response.setContentType("application/download;charset=utf-8");
        response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\";");
        response.setHeader("Content-Transfer-Encoding", "binary");

        workBook.write(response.getOutputStream());


        workBook.close();
        response.getOutputStream().close();
    }

}

