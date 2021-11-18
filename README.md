## 요구사항

### 유저
- [ ] 유저 도메인 구현
- [ ] 토큰방식 구현 -> Bearer X, Token {sadfnlksdaf}
- [ ] 로그인 성공시 response body에 토큰 포함
- [ ] DTO 구현
  - [ ] 로그인일때: accessToken string
  - [ ] 예외터지면: 400 + message
  - [ ] 회원가입일때: email, id(integer), userType(string 소문자), 유저생성시각(date-time), 최종수정시각(date-time)