package com.example.quiz.ui.result

import com.example.quiz.R
import androidx.compose.foundation.background
import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll

@Composable
fun ResultScreen(
    score: Int,
    onGoHome: () -> Unit,
    onShowWrongNote: () -> Unit,
    onShowRanking: () -> Unit
) {
    val scrollState = rememberScrollState()
    // 정답/오답 개수
    val correct = score/10
    val wrong = 10 - correct

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF2F6FF))
            .padding(24.dp)
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // 축하 메시지
        Text(
            text = when {
                score >= 90 -> "🎉 완벽해요!"
                score >= 70 -> "👏 훌륭해요!"
                score >= 50 -> "👍 잘했어요!"
                else -> "💪 다시 도전!"
            },
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF2D3436)
        )

        Spacer(modifier = Modifier.height(40.dp))

        // 점수 카드
        Card(
            modifier = Modifier
                .size(180.dp)
                .shadow(12.dp, CircleShape),
            shape = CircleShape,
            colors = CardDefaults.cardColors(containerColor = Color(0xFFF5F5F5))
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Your Score",
                        fontSize = 16.sp,
                        color = Color.Gray
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "$score",
                        fontSize = 52.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF5B9FED)
                    )
                    Text(
                        text = "점",
                        fontSize = 18.sp,
                        color = Color.Gray
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(30.dp))

        // 맞은 개수 / 틀린 개수
        Card(
            modifier = Modifier
                .width(300.dp)
                .height(75.dp)
                .padding(horizontal = 10.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(4.dp),
            shape = RoundedCornerShape(16.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("맞은 개수", color = Color.Gray, fontSize = 15.sp)
                    Text("${correct} 개", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                }

                Box(
                    modifier = Modifier
                        .width(1.dp)
                        .height(55.dp)
                        .background(Color(0xFFC1B7B7))
                )

                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("틀린 개수", color = Color.Gray, fontSize = 15.sp)
                    Text("${wrong} 개", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                }
            }
        }

        Spacer(modifier = Modifier.height(30.dp))

        // 버튼 그룹
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // 오답 노트
            Button(
                onClick = onShowWrongNote,
                modifier = Modifier
                    .weight(1f)
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFFF6B88)
                ),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(
                    "오답 노트",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Medium
                )
            }

            // 랭킹
            Button(
                onClick = onShowRanking,
                modifier = Modifier
                    .weight(1f)
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFFFA500)
                ),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(
                    "전체 랭킹",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Medium
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // 다시 시작하기 버튼
        Button(
            onClick = onGoHome,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF5B9FED)
            )
        ) {
            Text(
                text = "다시 시작하기",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }
    }
}

@Preview(showBackground = true, device = Devices.PIXEL_4)
@Composable
fun ResultScreenPreview() {
    ResultScreen(
        score = 80,
        onGoHome = {},
        onShowWrongNote = {},
        onShowRanking = {}
    )
}
