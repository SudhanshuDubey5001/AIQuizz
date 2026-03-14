# 🧠 AI Quiz App

<p align="center">
  <img src="app/src/main/res/mipmap-xxxhdpi/quiz_app_icon.webp" width="140"/>
</p>

<p align="center">
<b>An intelligent quiz application powered by AI that generates unique quiz questions in real time.</b>
</p>

<p align="center">
Generate unlimited quizzes across multiple topics with dynamic AI-powered question generation.
</p>

---

# ✨ Overview

**AI Quiz App** is a modern Android application that leverages AI APIs to dynamically generate quiz questions.  
Instead of relying on static question banks, the app creates **fresh questions on demand**, ensuring an engaging and non-repetitive learning experience.

The app focuses on:

- 🧠 Intelligent quiz generation
- 🔄 Duplicate question prevention
- ⚡ Fast and lightweight performance
- 📱 Modern Android UI using Jetpack Compose
- 🤖 AI-powered question creation

---

# 🚀 Features

## 🎯 Dynamic AI Quiz Generation
- Generates quiz questions **in real time using AI APIs**
- No static question database required
- Unlimited quizzes across multiple topics

---

## 🧩 Smart Duplicate Detection
Prevents repeating questions by using:

- Levenshtein similarity
- Semantic filtering
- Question history tracking

Ensures every quiz session feels fresh.

---

## 📝 Structured Quiz Format
Each generated quiz contains:

- Question text
- 4 answer options
- 1 correct answer
- Topic-specific logic

All delivered in **structured JSON format**.

---

## ⚡ Intelligent JSON Recovery
Handles imperfect AI responses safely:

- Extracts JSON from mixed text responses
- Cleans markdown formatting
- Recovers broken JSON blocks

Ensures the app never crashes due to malformed responses.

---

## 📱 Modern UI with Jetpack Compose
Built using the latest Android UI toolkit.

Advantages:

- Declarative UI
- Reactive state management
- Smooth animations
- Mobile-first responsive design

---

## 🔄 Adaptive Question Generation
Automatically removes the duplicate question:

1. AI generates **5 questions per API call**
2. App filters duplicates by using levenshteinDistance and jaccardSimilarity and excludes if score > 85%
3. Returns **best unique 5**
WARNING : Only filters 3 iterations (API calls) as duplicates might keep coming everytime. Fourth call is final question set irrespective of repeatition.  

This greatly improves quiz quality.

---

# 🏗 Architecture

The application follows a **modern Android architecture**:
1. Clean Architecture + Feature Modules
2. Modularized Android Architecture
3. Dagger Hilt for dependency injection


