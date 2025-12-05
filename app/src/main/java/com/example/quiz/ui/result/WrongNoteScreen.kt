package com.example.quiz.ui.result

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.quiz.R
import com.example.quiz.data.QuizDataManager

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WrongNoteScreen(onBack: () -> Unit) {
    val wrongList = QuizDataManager.currentWrongAnswers

    Scaffold(
        containerColor = Color(0xFFF2F6FF),
        topBar = {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .shadow(4.dp, RoundedCornerShape(16.dp)),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFF5F5F5)),
                shape = RoundedCornerShape(16.dp)
            ) {
                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = Color(0xFF2D3436))
                    }
                    Spacer(Modifier.width(8.dp))
                    Text("오답 노트", fontSize = 22.sp, fontWeight = FontWeight.Bold, color = Color(0xFF2D3436))
                }
            }
        }
    ) { innerPadding ->

        if (wrongList.isEmpty()) {
            // 비어있을 때는 전체 화면 가운데 표시
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                contentAlignment = Alignment.Center
            ) {
                EmptyWrongNoteCard()
            }

        } else {

            // 스크롤 가능한 리스트
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(wrongList) { wrong ->
                    SimpleWrongAnswerCard(wrong)
                }

                item { Spacer(modifier = Modifier.height(16.dp)) }
            }
        }
    }
}

@Composable
fun SimpleWrongAnswerCard(wrong: com.example.quiz.data.WrongAnswer) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(4.dp, RoundedCornerShape(16.dp)),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF5F5F5)),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            // 문제
            Row(
                verticalAlignment = Alignment.Top
            ) {
                Text(
                    text = "Q.",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFFF6B88)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = wrong.question.question,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFF2D3436),
                    lineHeight = 24.sp,
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))
            Divider(color = Color.LightGray.copy(alpha = 0.5f))
            Spacer(modifier = Modifier.height(16.dp))

            // 내 답변
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "❌",
                    fontSize = 20.sp
                )
                Spacer(modifier = Modifier.width(8.dp))
                Column {
                    Text(
                        text = "내 답변",
                        fontSize = 14.sp,
                        color = Color.Gray
                    )
                    Text(
                        text = wrong.question.choices[wrong.selectedIndex],
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFFFF6B88)
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // 정답
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "✅",
                    fontSize = 20.sp
                )
                Spacer(modifier = Modifier.width(8.dp))
                Column {
                    Text(
                        text = "정답",
                        fontSize = 12.sp,
                        color = Color.Gray
                    )
                    Text(
                        text = wrong.question.choices[wrong.question.answerIndex],
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF51CF66)
                    )
                }
            }
        }
    }
}

@Composable
fun WrongNoteList(
    wrongList: List<com.example.quiz.data.WrongAnswer>,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item { Spacer(modifier = Modifier.height(4.dp)) }

        items(wrongList) { wrong ->
            SimpleWrongAnswerCard(wrong = wrong)
        }

        item { Spacer(modifier = Modifier.height(16.dp)) }
    }
}

@Composable
fun EmptyWrongNoteCard() {
    Card(
        modifier = Modifier
            .wrapContentWidth()
            .wrapContentHeight()
            .shadow(4.dp, RoundedCornerShape(20.dp)),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF5F5F5)),
        shape = RoundedCornerShape(20.dp)
    ) {
        Column(
            modifier = Modifier.padding(40.dp),
            horizontalAlignment = Alignment.CenterHorizontally

        ) {
            Text("🎉", fontSize = 64.sp)
            Spacer(Modifier.height(16.dp))
            Text("완벽합니다!",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF51CF66),
                textAlign = TextAlign.Center
            )
            Text("틀린 문제가 없어요!", fontSize = 16.sp, color = Color.Gray, modifier = Modifier.padding(top = 8.dp))
        }
    }
}

@Preview(showBackground = true, device = Devices.PIXEL_4)
@Composable
fun WrongNoteScreenPreview() {
    WrongNoteScreen(
        onBack = {}
    )
}