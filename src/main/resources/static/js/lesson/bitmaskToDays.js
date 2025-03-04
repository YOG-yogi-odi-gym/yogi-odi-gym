function bitmaskToDays(bitmask) {
    if (bitmask === 31){
        return "평일"
    } else if (bitmask === 96){
        return "주말"
    }

    const days = ["월", "화", "수", "목", "금", "토", "일"]
    let result = []

    for (let i = 0; i < days.length; i++) {
        if (bitmask & (1 << i)) {
            result.push(days[i])
        }
    }
    return result.join(", ")
}