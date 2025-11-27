package com.example.quiz.ui.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.quiz.R

@Composable
fun MainScreen(
    onSelectCategory: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "언제나 재미있는 퀴즈!",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = "오늘도 재미있게 퀴즈 풀어봐요~",
            fontSize = 16.sp,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        // 상식 퀴즈
        CategoryCard(
            title = "상식 퀴즈",
            background = Color(0xFF678DFE),
            icon = R.drawable.ic_idea
        ) {
            onSelectCategory("common")
        }

        // 암산 퀴즈
        CategoryCard(
            title = "암산 퀴즈",
            background = Color(0xFFF0D96A),
            icon = R.drawable.ic_map //나중에 math 추가 후 변경
        ) {
            onSelectCategory("math")
        }

        // 수도 퀴즈
        CategoryCard(
            title = "수도 퀴즈",
            background = Color(0xFFE58CAE),
            icon = R.drawable.ic_map
        ) {
            onSelectCategory("capital")
        }
    }
}

@Composable
fun CategoryCard(
    title: String,
    background: Color,
    icon: Int,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp)
            .height(100.dp)
            .clickable { onClick() },
        colors = CardDefaults.cardColors(containerColor = background),
        shape = RoundedCornerShape(20.dp)
    ) {
        Row(
            modifier = Modifier.padding(20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(icon),
                contentDescription = null,
                modifier = Modifier.size(48.dp)
            )

            Spacer(modifier = Modifier.width(16.dp))

            Text(
                text = title,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )
        }
    }
}