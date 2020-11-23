# toy

# Strategy
- 동시 다발적인 요청에 의한 충돌을 막기 위하여 DB의 UNIQUE KEY를 활용하여 전략 수립
  - 뿌리기 API 
    - roomId와 token 값을 UNIQUE KEY로 설정하여, 동일한 채팅방 내에서 생성될 수 있는 token의 중복 문제 해결
    - 중복 생성되는 token의 insert를 막기 위하여 DuplicateKeyException을 catch하여 3번의 재시도 로직 수행
  - 받기 API
    - 여러 사용자의 요청을 선착순으로 처리하기 위하여 DB의 auto increment 기능을 사용
    - 사용자의 받기 API 요청 시 먼저 temp 테이블에 데이터 insert
    - 이때 발생하는 auto increment 값을 활용하여 유저 별 선착순 등수를 확인하고, 할당된 뿌리기 건을 획득 하도록 로직을 
  
