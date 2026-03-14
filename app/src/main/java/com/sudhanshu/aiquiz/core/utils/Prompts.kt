package com.sudhanshu.aiquiz.core.utils

import com.sudhanshu.aiquiz.feature_quiz.domain.model.Level
import java.util.DuplicateFormatFlagsException

object Prompts {
    fun validatePrompt(topic: String): String{
        return "is \"$topic\" a valid topic for which you can generate 10 quiz questions with multiple choices. only answer in yes or no"
    }

    fun generateQuizPrompt2(
        topics: String,
        level: Level
    ): String{
        return "Generate a quiz on the topics, \"$topics\" with the following properties - \n" +
                "1. Every question must have 4 options\n" +
                "2. There must be only 1 correct answer\n" +
                "3. There must be in total \"${AppConfigurationConstants.QUESTIONS_COUNT_PER_API_CALL}\" questions\n" +
                "4. There must be also an explanation of the correct answer\n" +
                "5. There are three levels of difficulty - Easy, Medium, Hard. Generate the questions with the difficulty level - \"$level\".   \n" +
                "6. The response needs to be in JSON \n" +
                "7. Do not write anything else except the JSON I requested.\n" +
                "8. Make sure the format of json follows the below data class so I can parse your response easily - \n"+
                "data class Quiz(\n" +
                "    val questions: List<Question>\n" +
                ")\n" +
                "\n" +
                "data class Question(\n" +
                "    val question: String,\n" +
                "    val difficulty: String,\n" +
                "    val correct_answer: String,\n" +
                "    val options: List<String>,\n" +
                "    val explanation: String\n" +
                ")"+
                "Give me a proper JSON format response. Make sure the JSON is properly written with accurate opening and closing braces and brackets and the strings are inside double quotes. Make sure you use some kind of JSON verification function to check the generated JSON and if there is any error then fix it and verify it again. \n" +
                "I want the full response so don't cut the response in mid way. PLEASE DOUBLE CHECK THE JSON FORMATTING as it is crucial that it is 100% correct. Also, please make sure that there is nothing in the response except the JSON. "
    }

    fun generateQuizPromptForNextQuestions(
        topics: String,
        level: Level
        ,duplicateQuestionList: List<String>
    ): String{
        return "Generate a quiz on the topics, \"$topics\" with the following properties - \n" +
                "1. Every question must have 4 options\n" +
                "2. There must be only 1 correct answer\n" +
                "3. There must be in total \"${AppConfigurationConstants.QUESTIONS_COUNT_PER_API_CALL}\" questions and different from the previous set of questions\n" +
                "4. There must be also an explanation of the correct answer\n" +
                "5. There are three levels of difficulty - Easy, Medium, Hard. Generate the questions with the difficulty level - \"$level\".   \n" +
                "6. The response needs to be in JSON \n" +
                "7. Do not write anything else except the JSON I requested.\n" +
                "8. DO NOT INCLUDE THESE QUESTIONS. KEEP IT AS EXCLUSION LIST - ${duplicateQuestionList.toString()}.\n" +
                "9. Do NOT repeat or mention any questions from the exclusion list.\n" +
                "10. If a question conflicts with the exclusion list, silently replace it with a new one.\n"
                "9. Make sure the format of json follows the below data class so I can parse your response easily - \n"+
                "data class Quiz(\n" +
                "    val questions: List<Question>\n" +
                ")\n" +
                "\n" +
                "data class Question(\n" +
                "    val question: String,\n" +
                "    val difficulty: String,\n" +
                "    val correct_answer: String,\n" +
                "    val options: List<String>,\n" +
                "    val explanation: String\n" +
                ")"+
                        "IMPORTANT:\n" +
                        "Return ONLY valid JSON.\n" +
                        "Do not include explanations, comments, or text outside JSON.\n" +
                        "Do not explain replacements.\n"
                "Give me a proper JSON format response. Make sure the JSON is properly written with accurate opening and closing braces and brackets and the strings are inside double quotes. Make sure you use some kind of JSON verification function to check the generated JSON and if there is any error then fix it and verify it again. \n" +
                "I want the full response so don't cut the response in mid way. PLEASE DOUBLE CHECK THE JSON FORMATTING as it is crucial that it is 100% correct. Also, please make sure that there is nothing in the response except the JSON. "
    }

    fun generateResources(
        topics: String
    ): String{
        return "For the topics - $topics. Provide some website resources with a brief introduction about those websites. Strictly provide the response in JSON following the schema - \n" +
                "{\n" +
                "  resources: [\n" +
                "     { url: \"url of the resource\",\n" +
                "      explanation: \"explanation of the website resource\"\n" +
                "     } ......more items \n" +
                "], \n" +
                "Keep the response in the above given JSON format. The response must only contain JSON"
    }

    fun generateQuizPrompt(
        topics: String,
        level: Level
    ):String{
        return "You are a strict JSON generator.\n" +
                "\n" +
                "Generate a quiz on the topic: $topics.\n" +
                "\n" +
                "Requirements:\n" +
                "1. Generate exactly ${AppConfigurationConstants.QUESTIONS_COUNT_PER_API_CALL} questions.\n" +
                "2. Each question must have exactly 4 options.\n" +
                "3. Only one option must be correct.\n" +
                "4. Include an explanation for the correct answer.\n" +
                "5. Difficulty level of every question must be \"$level\".\n" +
                "6. The response MUST be valid JSON.\n" +
                "7. Do NOT include any text, markdown, explanations, comments, or formatting outside the JSON.\n" +
                "8. The output must start with \"{\" and end with \"}\".\n" +
                "9. All keys and string values must use double quotes.\n" +
                "10. Ensure the JSON is syntactically valid and complete.\n" +
                "\n" +
                "The JSON must follow EXACTLY this structure:\n" +
                "\n" +
                "{\n" +
                "  \"questions\": [\n" +
                "    {\n" +
                "      \"question\": \"string\",\n" +
                "      \"difficulty\": \"string\",\n" +
                "      \"correct_answer\": \"string\",\n" +
                "      \"options\": [\"string\", \"string\", \"string\", \"string\"],\n" +
                "      \"explanation\": \"string\"\n" +
                "    }\n" +
                "  ]\n" +
                "}\n" +
                "\n" +
                "Rules:\n" +
                "- \"questions\" must contain exactly ${AppConfigurationConstants.QUESTIONS_COUNT_PER_API_CALL} objects.\n" +
                "- \"options\" must always contain exactly 4 strings.\n" +
                "- \"correct_answer\" must match exactly one value from the \"options\" list.\n" +
                "- The response must contain ONLY valid JSON and nothing else."
    }
}