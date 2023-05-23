# TIL--TestCode

## 목차
[[2023-05-23] 수동 테스트 VS 자동화된 테스트](#수동-테스트-VS-자동화-된-테스트)

---
### 수동 테스트 VS 자동화 된 테스트
<br>

[수동 테스트 코드](https://github.com/kimyubi/TIL--TestCode/commit/f5de1cac26584d62a8e5d45d627d52ce2907c6df)

<br>
과연 이 코드가 자동화를 한 테스트인가?

이렇게 콘솔에 출력하여 테스트를 한다면, 결국 최종 확인 주체는 결국 `사람`이다.

⇒ 콘솔에 출력된 내용을 보고, 코드가 잘 동작하는지 확인해야 함.  
⇒ 따라서, 다른 사람이 이 테스트 코드를 봤을 때 무엇을 검증해야 하는지 알 수 없다.  
⇒ 현재 테스트 코드는 검증 없이 콘솔에 출력만 하기 때문에 무조건 성공할 수 밖에 없는
테스트 코드이기 때문이다.  