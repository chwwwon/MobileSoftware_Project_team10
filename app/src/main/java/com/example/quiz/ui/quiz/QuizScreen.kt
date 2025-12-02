package com.example.quiz.ui.quiz

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.quiz.data.SoundManager
import com.example.quiz.ui.quiz.QuizViewModel
import com.example.quiz.ui.quiz.getCategoryName
import com.example.quiz.ui.quiz.components.AnswerButton
import com.example.quiz.ui.quiz.components.QuestionCard
import com.example.quiz.ui.theme.Pretendard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuizScreen(
    category: String,
    onFinish: (score: Int) -> Unit
) {
    val viewModel: QuizViewModel =
        viewModel(factory = QuizViewModel.provideFactory(category))

    val currentQuestion = viewModel.currentQuestion
    val remaining = (viewModel.totalQuestions - viewModel.currentIndex)
    val correctCount = viewModel.score / 10

    var showFeedback by remember { mutableStateOf(false) }
    var isCorrectAnswer by remember { mutableStateOf(false) }

    LaunchedEffect(viewModel.currentIndex) {
        showFeedback = false
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF2F6FF))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .padding(top = 12.dp)
        ) {
            // 상단 헤더
            SimpleTopBar(
                categoryName = getCategoryName(category),
                currentIndex = viewModel.currentIndex + 1,
                totalQuestions = viewModel.totalQuestions
            )

            // 남은 문제 & 정답 수
            SimpleStatsCard(
                remaining = remaining,
                correct = correctCount
            )

            Spacer(modifier = Modifier.height(32.dp))

            // 문제 카드
            SimpleQuestionCard(questionText = currentQuestion.question)

            Spacer(modifier = Modifier.height(40.dp))

            // 보기 버튼들
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                currentQuestion.choices.forEachIndexed { index, choice ->
                    val bgColor =
                        if (viewModel.isAnswerChecked) {
                            when {
                                index == viewModel.currentQuestion.answerIndex -> Color(0xFF5B9FED)
                                index == viewModel.selectedAnswerIndex -> Color(0xFFFF6B88)
                                else -> Color(0xFFE0E0E0)
                            }
                        } else {
                            if (viewModel.selectedAnswerIndex == index)
                                Color(0xFF5B9FED)
                            else
                                Color.White
                        }

                    SimpleAnswerButton(
                        text = choice,
                        backgroundColor = bgColor,
                        enabled = !viewModel.isAnswerChecked,
                        onClick = {
                            viewModel.selectAnswer(index)
                            // 즉시 제출
                            val submitted = viewModel.submitAnswer()
                            if (submitted) {
                                isCorrectAnswer = viewModel.isCorrect == true
                                showFeedback = true

                                if (isCorrectAnswer) SoundManager.playCorrect()
                                else SoundManager.playWrong()
                            }
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            // 다음 문제 버튼 (정답 확인 후에만 표시)
            if (viewModel.isAnswerChecked) {
                Button(
                    onClick = {
                        if (viewModel.isLastQuestion()) {
                            viewModel.saveRank()
                            onFinish(viewModel.score)
                        } else {
                            showFeedback = false
                            viewModel.goToNext()
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 20.dp)
                        .height(56.dp),
                    shape = RoundedCornerShape(28.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF5B9FED)
                    )
                ) {
                    Text(
                        if (viewModel.isLastQuestion()) "결과 보기" else "다음",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        fontFamily = Pretendard
                    )
                }
            }
        }

        // 정답/오답 피드백 배너
        if (showFeedback) {
            FeedbackBanner(
                isCorrect = isCorrectAnswer,
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(horizontal = 40.dp)
            )
        }
    }
}

@Composable
fun FeedbackBanner(isCorrect: Boolean, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .shadow(16.dp, RoundedCornerShape(20.dp)),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = if (isCorrect) "⭕" else "❌",
                fontSize = 64.sp,
                fontFamily = Pretendard
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = if (isCorrect) "정답입니다!" else "오답입니다!",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = if (isCorrect) Color(0xFF51CF66) else Color(0xFFFF6B88),
                fontFamily = Pretendard
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = if (isCorrect) "잘하셨어요!" else "다음엔 맞출 수 있어요!",
                fontSize = 14.sp,
                color = Color.Gray,
                fontFamily = Pretendard
            )
        }
    }
}

@Composable
fun SimpleTopBar(categoryName: String, currentIndex: Int, totalQuestions: Int) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .shadow(4.dp, RoundedCornerShape(16.dp)),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = categoryName,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF2D3436),
                fontFamily = Pretendard
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "문제 $currentIndex / $totalQuestions",
                fontSize = 16.sp,
                color = Color.Gray,
                fontFamily = Pretendard
            )

            // 진행률 바
            Spacer(modifier = Modifier.height(12.dp))
            LinearProgressIndicator(
                progress = currentIndex.toFloat() / totalQuestions.toFloat(),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp),
                color = Color(0xFF5B9FED),
                trackColor = Color(0xFFE0E0E0)
            )
        }
    }
}

@Composable
fun SimpleStatsCard(remaining: Int, correct: Int) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .shadow(4.dp, RoundedCornerShape(16.dp)),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    "남은 문제",
                    fontSize = 16.sp,
                    color = Color.Gray,
                    fontFamily = Pretendard
                )
                Text(
                    "${remaining}개",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFFF6B88),
                    modifier = Modifier.padding(top = 3.dp),
                    fontFamily = Pretendard
                )
            }

            Divider(
                modifier = Modifier
                    .height(60.dp)
                    .width(1.dp),
                color = Color.LightGray
            )

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    "정답 수",
                    fontSize = 16.sp,
                    color = Color.Gray,
                    fontFamily = Pretendard
                )
                Text(
                    "${correct}개",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF51CF66),
                    modifier = Modifier.padding(top = 3.dp),
                    fontFamily = Pretendard
                )
            }
        }
    }
}

@Composable
fun SimpleQuestionCard(questionText: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
            .shadow(8.dp, RoundedCornerShape(20.dp)),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(20.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(32.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = questionText,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                lineHeight = 28.sp,
                color = Color(0xFF2D3436),
                fontFamily = Pretendard
            )
        }
    }
}

@Composable
fun SimpleAnswerButton(
    text: String,
    backgroundColor: Color,
    enabled: Boolean,
    onClick: () -> Unit
) {
    val clickableModifier =
        if (enabled) Modifier.clickable { onClick() }
        else Modifier

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .shadow(4.dp, RoundedCornerShape(12.dp))
            .then(clickableModifier),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = backgroundColor)
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = text,
                fontSize = 17.sp,
                color = if (backgroundColor == Color.White) Color(0xFF2D3436) else Color.White,
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 20.dp),
                fontFamily = Pretendard
            )
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

@Preview(showBackground = true, device = Devices.PIXEL_4)
@Composable
fun QuizScreenPreview() {
    QuizScreen(
        category = "capital",
        onFinish = {}
    )
}