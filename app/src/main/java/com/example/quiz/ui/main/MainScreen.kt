package com.example.quiz.ui.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
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
import com.example.quiz.ui.theme.Pretendard
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll

@Composable
fun MainScreen(
    onSelectCategory: (String) -> Unit,
    onShowRanking: () -> Unit
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF2F6FF))
            .padding(24.dp)
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(40.dp))

        // 타이틀
        Text(
            text = "언제나 재미있는 퀴즈!",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF2D3436),
            fontFamily = Pretendard
        )
        Text(
            text = "퀴즈 주제를 골라보세요~",
            fontSize = 16.sp,
            color = Color.Gray,
            modifier = Modifier.padding(top = 8.dp, bottom = 40.dp),
            fontFamily = Pretendard
        )

        // 상식 퀴즈
        SimpleCategoryCard(
            title = "상식 퀴즈",
            background = Color(0xFFBAD1F9),
            iconLeft = R.drawable.ic_idea,
            iconRight = R.drawable.ic_idea
        ) {
            onSelectCategory("common")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // 암산 퀴즈
        SimpleCategoryCard(
            title = "암산 퀴즈",
            background = Color(0xFF5D9BFB),
            iconLeft = R.drawable.math,
            iconRight = R.drawable.math
        ) {
            onSelectCategory("math")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // 수도 퀴즈
        SimpleCategoryCard(
            title = "수도 퀴즈",
            background = Color(0xFFFFE27A),
            iconLeft = R.drawable.ic_map,
            iconRight = R.drawable.ic_map
        ) {
            onSelectCategory("capital")
        }

        Spacer(modifier = Modifier.height(40.dp))

        // 랭킹 버튼
        Button(
            onClick = onShowRanking,
            modifier = Modifier
                .fillMaxWidth()
                .height(74.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFD7D7D7)
            ),
            shape = RoundedCornerShape(12.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)
            ) {
                Image(
                    painter = painterResource(R.drawable.ranking),
                    contentDescription = null,
                    modifier = Modifier.size(48.dp)
                )

                Spacer(modifier = Modifier.width(12.dp))

                Text(
                    text = "명예의 전당",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.DarkGray,
                    fontFamily = Pretendard
                )

                Spacer(modifier = Modifier.width(12.dp))

                Image(
                    painter = painterResource(R.drawable.ranking),
                    contentDescription = null,
                    modifier = Modifier.size(48.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))
    }
}

@Composable
fun SimpleCategoryCard(
    title: String,
    background: Color,
    iconLeft: Int,
    iconRight: Int,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp)
            .shadow(4.dp, RoundedCornerShape(16.dp))
            .clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = background)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // 왼쪽 아이콘
            Image(
                painter = painterResource(iconLeft),
                contentDescription = null,
                modifier = Modifier.size(48.dp)
            )

            // 제목
            Text(
                text = title,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
                fontFamily = Pretendard
            )

            // 오른쪽 아이콘
            Image(
                painter = painterResource(iconRight),
                contentDescription = null,
                modifier = Modifier.size(48.dp)
            )
        }
    }
}

@Preview(showBackground = true, device = Devices.PIXEL_4)
@Composable
fun MainScreenPreview() {
    MainScreen(
        onSelectCategory = {},
        onShowRanking = {}
    )
}