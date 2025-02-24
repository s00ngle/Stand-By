package hellojpa;

import jakarta.persistence.*;

import java.io.PrintStream;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class JpaMain {

    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();

        tx.begin();

        try {
//            insert sql
//            Member member = new Member();
//            member.setId(2L);
//            member.setName("HelloB");
//            em.persist(member);

//            select sql
//            Member findMember = em.find(Member.class, 2L);
//            System.out.println("findMember = " + findMember.getId());
//            System.out.println("findMember = " + findMember.getName());

//            delete sql
//            Member findMember = em.find(Member.class, 2L);
//            em.remove(findMember);

//            update dql
//            Member findMember = em.find(Member.class, 2L);
//            findMember.setName("HelloJPA")

//            위는 어떻게 실행되는걸까?
//            jpa를 통해서 가져오면 jpa가 관리를 시작한다.
//            데이터가 변경됬는지 확인하고 커밋한다.
//            이때 변경됐으면 update 쿼리를 날린다.

//            enum 타입 활용법
            Member member1 = new Member();

            member1.setId(1L);
            member1.setUsername("HelloJPA1");
            // 아래와 같이 이넘타입을 사용가능하다.
            member1.setRoleType(RoleType.ADMIN);
            member1.setLocalDate(LocalDate.now());
            member1.setLocalDateTime(LocalDateTime. now());
            em.persist(member1);

            // enum 타입 활용법
            Member member2 = new Member();
            member2.setId(2L);
            member2.setUsername("HelloJPA2");
            // 아래와 같이 이넘타입을 사용가능하다.
            member2.setRoleType(RoleType.USER);
            em.persist(member2);




            tx.commit();
        } catch (Exception e) {
            System.out.println("롤백됨");
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();
    }
}
