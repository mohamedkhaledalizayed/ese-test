package com.neqabty.domain.common

import io.reactivex.ObservableTransformer

abstract class Transformer<T> : ObservableTransformer<T, T>