package com.example.quiz.ui.result

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.quiz.R
import com.example.quiz.data.QuizDataManager

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RankingScreen(onBack: () -> Unit) {
    val rankingList = QuizDataManager.rankingList

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF2F6FF))
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            // 상단 바
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .shadow(4.dp, RoundedCornerShape(16.dp)),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFF5F5F5)),
                shape = RoundedCornerShape(16.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = onBack) {
                        Icon(
                            Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = Color(0xFF2D3436)
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        "명예의 전당",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF2D3436)
                    )
                }
            }

            if (rankingList.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(24.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .shadow(4.dp, RoundedCornerShape(20.dp)),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFF5F5F5)),
                        shape = RoundedCornerShape(20.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(40.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                "🏆",
                                fontSize = 64.sp
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            Text(
                                "아직 기록이 없습니다",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.Gray
                            )
                            Text(
                                "첫 번째 도전자가 되어보세요!",
                                fontSize = 14.sp,
                                color = Color.LightGray,
                                modifier = Modifier.padding(top = 8.dp)
                            )
                        }
                    }
                }
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    item { Spacer(modifier = Modifier.height(4.dp)) }

                    itemsIndexed(rankingList) { index, rank ->
                        RankingCardWithMedal(
                            rank = index + 1,
                            entry = rank
                        )
                    }

                    item { Spacer(modifier = Modifier.height(16.dp)) }
                }
            }
        }
    }
}

@Composable
fun RankingCardWithMedal(rank: Int, entry: com.example.quiz.data.RankingEntry) {
    val medalDrawable = when (rank) {
        1 -> R.drawable.imagegold
        2 -> R.drawable.imagesilver
        3 -> R.drawable.bronzemedal
        else -> null
    }

    val backgroundColor = when (rank) {
        1 -> Color(0xFFFFF9E6)
        2 -> Color(0xFFF5F5F5)
        3 -> Color(0xFFFFE8D6)
        else -> Color.White
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(if (rank <= 3) 8.dp else 4.dp, RoundedCornerShape(16.dp)),
        colors = CardDefaults.cardColors(containerColor = backgroundColor),
        shape = RoundedCornerShape(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // 메달 또는 순위 번호
            if (medalDrawable != null) {
                Image(
                    painter = painterResource(medalDrawable),
                    contentDescription = "Medal",
                    modifier = Modifier.size(48.dp)
                )
            } else {
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .background(Color(0xFFE0E0E0), CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = rank.toString(),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF757575)
                    )
                }
            }

            Spacer(modifier = Modifier.width(16.dp))

            // 정보
            Column(modifier = Modifier.weight(1f)) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = entry.topic,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF2D3436)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Card(
                        colors = CardDefaults.cardColors(
                            containerColor = when (rank) {
                                1 -> Color(0xFFFFD700).copy(alpha = 0.2f)
                                2 -> Color(0xFFC0C0C0).copy(alpha = 0.3f)
                                3 -> Color(0xFFCD7F32).copy(alpha = 0.2f)
                                else -> Color(0xFF5B9FED).copy(alpha = 0.2f)
                            }
                        ),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(
                            text = "${entry.score}점",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = when (rank) {
                                1 -> Color(0xFFFF8C00)
                                2 -> Color(0xFF757575)
                                3 -> Color(0xFFCD7F32)
                                else -> Color(0xFF5B9FED)
                            },
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp)
                        )
                    }
                }
                Text(
                    text = entry.date,
                    fontSize = 12.sp,
                    color = Color.Gray,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }
        }
    }
}

@Preview(showBackground = true, device = Devices.PIXEL_4)
@Composable
fun RankingScreenPreview() {
    RankingScreen(
        onBack = {}
    )
}