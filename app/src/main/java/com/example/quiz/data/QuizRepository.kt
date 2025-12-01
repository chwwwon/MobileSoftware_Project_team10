package com.example.quiz.data

object QuizRepository {

    fun getQuestions(category: String): List<QuizQuestion> {
        return when (category) {
            "common" -> commonQuestions()
            "math" -> mathQuestions()
            "capital" -> capitalQuestions()
            else -> commonQuestions()
        }
    }

    private fun commonQuestions() = listOf(
        QuizQuestion(
            question = "대한민국의 법적 성년 나이는 몇 세인가요?",
            choices = listOf("17세", "18세", "19세", "20세"),
            answerIndex = 2
        ),
        QuizQuestion(
            question = "해는 어디에서 뜰까?",
            choices = listOf("동쪽", "서쪽", "남쪽", "북쪽"),
            answerIndex = 0
        ),
        QuizQuestion(
            question = "세계에서 가장 큰 대륙은?",
            choices = listOf("아시아", "유럽", "아프리카", "남아메리카"),
            answerIndex = 0
        ),
        QuizQuestion(
            question = "인류 역사상 가장 오래된 문명은?",
            choices = listOf("이집트 문명", "메소포타미아 문명", "인도 문명", "중국 문명"),
            answerIndex = 2
        ),
        QuizQuestion(
            question = "세계에서 가장 높은 산은?",
            choices = listOf("에베레스트", "막강산", "킬리만자로", "안나푸르나"),
            answerIndex = 0
        ),
        QuizQuestion(
            question = "소음 측정 단위는 어느것인가?",
            choices = listOf("BOD", "ppm", "dB", "COD"),
            answerIndex = 2
        ),
        QuizQuestion(
            question = "무위자연이라는 사상을 주장한 고대 중국 철학자는?",
            choices = listOf("노자", "맹자", "공자", "순자"),
            answerIndex = 0
        ),
        QuizQuestion(
            question = "진화론을 주장한 책  『종의 기원』의 저자는?",
            choices = listOf("멘델", "헤밍웨이", "뉴턴", "다윈"),
            answerIndex = 3
        ),
        QuizQuestion(
            question = "세계에서 가장 깊은 호수는?",
            choices = listOf("카스피해", "빅토리아 호수", "탕가니카 호수", "바이칼 호수"),
            answerIndex = 3
        ),
        QuizQuestion(
            question = "세계에서 가장 작은 새는?",
            choices = listOf("참새", "벌새", "핀치", "제비"),
            answerIndex = 1
        )
    )

    private fun mathQuestions() = listOf(
        QuizQuestion("156 - 49= ?", listOf("97", "99", "107", "109"), 2),
        QuizQuestion("81 + 9 - 3 = ?", listOf("86", "87", "88", "89"), 1),
        QuizQuestion("2 x 7 + 34 = ?", listOf("38", "48", "68", "69"), 2),
        QuizQuestion("98 x 1 + 33 = ?", listOf("111", "121", "123", "131"), 3),
        QuizQuestion("22 - 1 x 33 = ?", listOf("693", "673", "666", "637"), 0),
        QuizQuestion("45 + 27 = ?", listOf("62", "66", "68", "70"), 2),
        QuizQuestion("120 ÷ 5 = ?", listOf("24", "26", "28", "30"), 0),
        QuizQuestion("13 x 6 = ?", listOf("66", "68", "70", "72"), 3),
        QuizQuestion("250 - 87 = ?", listOf("153", "163", "173", "183"), 1),
        QuizQuestion("(14 + 9) x 3 = ?", listOf("69", "72", "75", "81"), 3)
    )

    private fun capitalQuestions() = listOf(
        QuizQuestion("프랑스의 수도는?", listOf("파리", "마드리드", "베를린", "런던"), 0),
        QuizQuestion("일본의 수도는?", listOf("오사카", "도쿄", "교토", "삿포로"), 1),
        QuizQuestion("이탈리아의 수도는?", listOf("나폴리", "밀라노", "로마", "피렌체"), 2),
        QuizQuestion("캐나다의 수도는?", listOf("토론토", "오타와", "밴쿠버", "몬트리올"), 1),
        QuizQuestion("호주의 수도는?", listOf("시드니", "멜버른", "브리즈번", "캔버라"), 3),
        QuizQuestion("브라질의 수도는?", listOf("상파울루", "포르투 알레그레", "리우데자네이루", "브라질리아"), 3),
        QuizQuestion("대한민국의 수도는?", listOf("서울", "부산", "대구", "인천"), 0),
        QuizQuestion("중국의 수도는?", listOf("상하이", "광저우", "베이징", "칭다오"), 2),
        QuizQuestion("러시아의 수도는?", listOf("상트페테르부르크", "모스크바", "노보시비르스크", "카잔"), 1),
        QuizQuestion("이집트의 수도는?", listOf("카이로", "알렉산드리아", "기자", "포트사이드"), 0)
    )
}