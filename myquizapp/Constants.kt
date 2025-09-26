package my.first.myquizapp

object Constants {

    const val USER_NAME : String = "user_name"
    const val TOTAL_QUESTIONS : String = "total_questions"
    const val CORRECT_ANSWERS : String = "correct_answers"

    fun getQuestion():ArrayList<Question>{
        val questionsList = ArrayList<Question>()
        val qus1 = Question(
            1,
            "What Country Does This Flag Belongs To?",
            R.drawable.ic_flag_of_argentina,
            "Argentina",
            "Australia",
            "Arminia",
            "Austria",
            1
        )
        questionsList.add(qus1)

        val qus2 = Question(
            2,
            "What Country Does This Flag Belongs To?",
            R.drawable.ic_flag_of_belgium,
            "India",
            "Belgium",
            "Japan",
            "Canada",
            2
        )
        questionsList.add(qus2)
        val qus3 = Question(
            3,
            "What Country Does This Flag Belongs To?",
            R.drawable.ic_flag_of_australia,
            "France",
            "Australia",
            "India",
            "Egypt",
            2
        )
        questionsList.add(qus3)
        val qus4 = Question(
            4,
            "What Country Does This Flag Belongs To?",
            R.drawable.ic_flag_of_brazil,
            "Brazil",
            "Germany",
            "SouthKorea",
            "Japan",
            1
        )
        questionsList.add(qus4)
        val qus5 = Question(
            5,
            "What Country Does This Flag Belongs To?",
            R.drawable.ic_flag_of_denmark,
            "Argentina",
            "France",
            "Mexico",
            "Denmark",
            4
        )
        questionsList.add(qus5)
        val qus6 = Question(
            6,
            "What Country Does This Flag Belongs To?",
            R.drawable.ic_flag_of_fiji,
            "Russia",
            "Indonesia",
            "Fiji",
            "Mexico",
            3
        )
        questionsList.add(qus6)
        val qus7 = Question(
            7,
            "What Country Does This Flag Belongs To?",
            R.drawable.ic_flag_of_india,
            "Turkey",
            "Spain",
            "Japan",
            "India",
            4
        )
        questionsList.add(qus7)
        val qus8 = Question(
            8,
            "What Country Does This Flag Belongs To?",
            R.drawable.ic_flag_of_kuwait,
            "Saudi Arabia",
            "Kuwait",
            "Italy",
            "Canada",
            2
        )
        questionsList.add(qus8)
        val qus9 = Question(
            9,
            "What Country Does This Flag Belongs To?",
            R.drawable.ic_flag_of_new_zealand,
            "New Zealand",
            "United States",
            "China",
            "Shri Lanka",
            1
        )
        questionsList.add(qus9)
        val qus10 = Question(
            10,
            "What Country Does This Flag Belongs To?",
            R.drawable.ic_flag_of_germany,
            "Russia",
            "Germany",
            "Pakistan",
            "Canada",
            2
        )
        questionsList.add(qus10)
        return questionsList
    }
}