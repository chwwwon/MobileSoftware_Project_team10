package com.example.quiz.ui.quiz

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.quiz.data.SoundManager
import com.example.quiz.ui.quiz.QuizViewModel
import com.example.quiz.ui.quiz.getCategoryName
import com.example.quiz.ui.quiz.components.AnswerButton
import com.example.quiz.ui.quiz.components.QuestionCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuizScreen(
    category: String,
    onFinish: (score: Int) -> Unit
) {
    val viewModel: QuizViewModel =
        viewModel(factory = QuizViewModel.provideFactory(category))

    val currentQuestion = viewModel.currentQuestion
    val remaining = (viewModel.totalQuestions - viewModel.currentIndex - 1)
    val correctCount = viewModel.score / 10

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF8F8F8))
    ) {
        // 상단 바
        TopBar(categoryName = getCategoryName(category))

        // 통계바
        StatsBar(
            remaining = remaining,
            correct = correctCount
        )

        Spacer(modifier = Modifier.height(20.dp))

        QuestionCard(questionText = currentQuestion.question)

        Spacer(modifier = Modifier.height(32.dp))

        // 보기 버튼들
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            currentQuestion.choices.forEachIndexed { index, choice ->

                val bgColor =
                    if (viewModel.isAnswerChecked) {
                        when {
                            index == viewModel.currentQuestion.answerIndex -> Color(0xFF4CAF50)
                            index == viewModel.selectedAnswerIndex -> Color(0xFFE57373)
                            else -> Color(0xFFE0E0E0)
                        }
                    } else {
                        if (viewModel.selectedAnswerIndex == index) Color(0xFF90CAF9)
                        else Color(0xFFE0E0E0)
                    }

                AnswerButton(
                    text = choice,
                    backgroundColor = bgColor,
                    enabled = !viewModel.isAnswerChecked,
                    onClick = { viewModel.selectAnswer(index) }
                )

                Spacer(modifier = Modifier.height(14.dp))
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        // 제출 / 다음 버튼
        BottomSubmitNextButton(
            viewModel = viewModel,
            onFinish = onFinish
        )
    }
}

@Composable
fun TopBar(categoryName: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(vertical = 16.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = categoryName,
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun StatsBar(remaining: Int, correct: Int) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text("남은 개수", fontSize = 16.sp, color = Color.Gray)
            Text("${remaining}개", fontSize = 20.sp, fontWeight = FontWeight.Bold)
        }

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text("맞은 개수", fontSize = 16.sp, color = Color.Gray)
            Text("${correct}개", fontSize = 20.sp, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
fun QuizChoiceButton(
    text: String,
    backgroundColor: Color,
    enabled: Boolean,
    onClick: () -> Unit
) {
    val clickableModifier =
        if (enabled) Modifier.clickable { onClick() }
        else Modifier

    Box(
        modifier = Modifier
            .fillMaxWidth(0.9f)
            .height(65.dp)
            .then(clickableModifier)
            .background(backgroundColor, shape = RoundedCornerShape(12.dp)),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text,
            fontSize = 20.sp,
            color = Color.Black,
            fontWeight = FontWeight.Medium
        )
    }
}

@Composable
fun BottomSubmitNextButton(
    viewModel: QuizViewModel,
    onFinish: (score: Int) -> Unit
) {
    val buttonHeight = 70.dp

    if (!viewModel.isAnswerChecked) {
        Button(
            onClick = {
                val submitted = viewModel.submitAnswer()
                if (submitted) {
                    if (viewModel.isCorrect == true) SoundManager.playCorrect()
                    else SoundManager.playWrong()
                }
            },
            enabled = viewModel.selectedAnswerIndex != -1,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 20.dp)
                .height(buttonHeight),
            shape = RoundedCornerShape(40.dp)
        ) {
            Text("제출하기", fontSize = 20.sp)
        }
    } else {
        Button(
            onClick = {
                if (viewModel.isLastQuestion()) {
                    viewModel.saveRank()
                    onFinish(viewModel.score)
                } else {
                    viewModel.goToNext()
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 20.dp)
                .height(buttonHeight),
            shape = RoundedCornerShape(40.dp)
        ) {
            Text("다음 문제", fontSize = 20.sp)
        }
    }
}

fun getCategoryName(category: String): String =
    when (category) {
        "common" -> "상식 퀴즈"
        "math" -> "암산 퀴즈"
        "capital" -> "수도 퀴즈"
        else -> "퀴즈"
    }