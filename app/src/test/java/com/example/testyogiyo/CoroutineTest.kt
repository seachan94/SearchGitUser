package com.example.testyogiyo

import kotlinx.coroutines.test.TestCoroutineScope

interface CoroutineTest {
    var testScope: TestCoroutineScope
}