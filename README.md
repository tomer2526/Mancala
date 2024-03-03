הצעת פרויקט י"ג- הנדסת תוכנה

סמל מוסד: 470054
שם המכללה: מכללת אורט רחובות
שם הסטודנט: תומר שמי	
 תז הסטודנט:  215033606
שם הפרויקט:  Mancala

רקע תאורטי בתחום הפרויקט: 
 המנקלה הוא אחד המשחקים העתיקים והפופולריים ביותר בעולם, ונוצרו לו יותר מ- 400 גרסאות. מנקלה משוחק זה אלפי שנים במצרים, שם נמצאו לוחות משחק מגולפים באבני הפירמידה במקדש לוקסור. המשחק התפשט לאסיה ואפריקה, שם הוא התפתח לגרסאות שונות. טיילים אירופאים נתקלו במשחק בקהיר במאה התשע-עשרה והביאו אותו לאירופה, ובמקביל, עבדים אפריקאים הביאו איתם את המשחק למערב הודו ומשם הוא הגיע עד לסין. 
עד היום, באזורים כפריים רבים באפריקה ניתן לראות ילדים משחקים את המשחק עם בורות שהם חופרים באדמה.

תיאור הפרויקט: 
המשחק מנקלה מכיל לוח בו קופה ושש גומות לכל משתתף. 
מטרת המשחק היא לאסוף מספר רב יותר של גולות לקופה שלך. 
מנקלה הוא משחק אסטרטגי ואין בו שום אלמנט של מזל, גורל המשחק תלוי רק במהלכים של השחקנים ולפי החשיבה שלהם. 

לוח המשחק: 
לוח המשחק בנוי משש גומות וקופה לכל שחקן, בתחילת המשחק יש 4 גולות בכל גומה.
כך הלוח נראה בתחילת המשחק:
 

כללי המשחק:
המשחק הוא רק ל – 2 משתתפים. בכל תור השחקן בוחר גומה (בה יש לפחות גולה אחת) ומתחיל לפזר את כל הגולות שבאותה הגומה כנגד כיוון השעון, חרוז אחד בכל גומה שנמצאת אחריה, אם החרוז האחרון מונח בקופה של השחקן הוא זוכה בתור נוסף, אם החרוז האחרון מונח בגומה ריקה (של השחקן עצמו) הוא מקבל את הגולות שבגומה המקבילה וגם את הגולה שנכנסה לגומה הריקה. 

מטרה: 
לאסוף כמה שיותר גולות לקופה.
שלב ההתחלה: תחילה, השחקנים צריכים לבחור מי עושה את התור הראשון. 
תנועה על-גבי הלוח: השחקן בוחר גומה (בה יש לפחות גולה אחת) ומתחיל לפזר את כל הגולות שבאותה הגומה כנגד כיוון השעון, חרוז אחד בכל גומה שנמצאת אחריה, אם החרוז האחרון מונח בקופה של השחקן הוא זוכה בתור נוסף. לדוגמה: 
 
במקרה זה, שחקן 1 קיבל תור נוסף כיוון שהגולה האחרונה שלו נכנסה לקופה שלו. 

בדוגמה זו השחקן השני קיבל את הגולות שבגומה המקבילה של השחקן הראשון כיוון שהגולה שלו נחתה בגומה ריקה בצד שלו:

אם בסוף התור כל הגומות של השחקן ריקות הוא יקבל את כל הגולות שנותרו בגומות של השחקן היריב. 
המשחק יסתיים כאשר כל הגומות ריקות, והמנצח הוא השחקן בעל מספר הגולות הגבוה ביותר בקופה שלו. 

הגדרת הבעיה האלגוריתמית: הבעיה האלגוריתמית תהיה יצירת אלגוריתם ברמות שונות לשחקן הממוחשב (CPU) שישחק במשחק.
בכל תור בו משחק המחשב, יחושבו כל המהלכים החוקיים שניתן לעשות בלוח הנוכחי, כל מהלך יקבל ציון לפי אסטרטגיה המבוססת על חוקי המשחק לדוגמה, יש עדיפות גבוהה יותר לסיום התור בגומה ריקה (במידה ובגומה המקבילה יש מספר כדאי של גולות) מאשר סיום התור בקופה וקבלת תור נוסף.

תהליכים עיקריים בפרויקט: 
●	תהיה גרסת שחקן נגד שחקן
●	מחשב נגד מחשב – ברמות שונות
●	בניית ממשק ידידותי למשתמש
●	המערכת תבצע בדיקות תקינות כגון: בדיקה שלא ניתן לבחור גומה של שחקן שלא תורו, בדיקה שלא ניתן לבחור גומה ריקה וכו...
●	בדיקות הקלט ובדיקת מצבי ההכרעה ישמו דגש על יעילות זמן ריצה.
●	המערכת תכריז על הודעות מתאימות כמו הכרזה על המנצח לפי תנאי סיום המשחק.
●	המערכת תאכוף את חוקי המשחק באופן אוטומטי כגון: אם השחקן סיים את תורו בגומה ריקה המערכת תאסוף לקופה שלו את הגומות המקבילות בנוסף לגולה שנכנסה לגומה הריקה אך רק אם יש גולות בקופה המקבילה והתור נגמר בצדו של השחקן ולא של היריב. 

תיאור הטכנולוגיה: 
שפות תכנות:  java 
מפרט טכני: 
זיכרון: 16 GB RAM
מעבד: 1M
סביבת עבודה: macOS 14
תוכנה: IntelliJ IDEA
