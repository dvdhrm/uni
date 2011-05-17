#define UPPER 15
const int lower = 12;

int sum = 0;

int main(void)
{
	int i;
	for (i = lower; i < UPPER; i++) {
		sum += i;
	}
	return sum;
}
