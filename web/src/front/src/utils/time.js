export function timeForToday(value) {    
    const today = new Date();
    const timeValue = new Date(value);

    const betweenTime = Math.floor((today.getTime() - timeValue.getTime()) / 1000 / 60);
    if (betweenTime < 1) return '방금 전';
    if (betweenTime < 60) {
        return `${betweenTime}분 전`;
    }

    const betweenTimeHour = Math.floor(betweenTime / 60);
    if (betweenTimeHour < 24) {
        return `${betweenTimeHour}시간 전`;
    }

    const betweenTimeDay = Math.floor(betweenTime / 60 / 24);
    if (betweenTimeDay < 7) {
        return `${betweenTimeDay}일 전`;
    }

    const betweenTimeWeek = Math.floor(betweenTime / 60 / 24 / 7);
    if (betweenTimeWeek < 4) {
        return `${betweenTimeWeek}주 전`;
    }

    const betweenTimeMonth = Math.floor(betweenTime / 60 / 24 / 7 / 4);
    if (betweenTimeMonth < 12) {
        return `${betweenTimeMonth}달 전`;
    }

    // return `${Math.floor(betweenTimeMonth / 12)}년 전`;
    return value;
}